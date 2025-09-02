package org.rapidcargo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rapidcargo.EntryMovement;
import org.rapidcargo.ExitMovement;
import org.rapidcargo.Goods;
import org.rapidcargo.Movement;
import org.rapidcargo.enums.CustomsStatus;
import org.rapidcargo.enums.ReferenceType;
import org.rapidcargo.exception.BusinessException;
import org.rapidcargo.mapper.EntityMapper;
import org.rapidcargo.repository.MovementRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovementServiceTest {

    @Mock
    private MovementRepository repository;

    @Mock
    private MovementValidationService validator;

    @Mock
    private XmlGeneratorService xmlGenerator;

    @Mock
    private EmailService emailService;

    @Mock
    private EntityMapper entityMapper;

    private MovementService movementService;
    private EntryMovement entryMovement;
    private ExitMovement exitMovement;
    private Goods validGoods;

    @BeforeEach
    void setUp() {
        movementService = new MovementService(
                repository, validator, xmlGenerator, emailService, entityMapper
        );

        validGoods = new Goods(
                ReferenceType.AWB,
                "12345678901",
                10,
                new BigDecimal("15.500"),
                100,
                new BigDecimal("155.000"),
                "Test goods"
        );

        entryMovement = new EntryMovement();
        entryMovement.setId(1L);
        entryMovement.setMovementTime(LocalDateTime.now());
        entryMovement.setCreatedBy("test-user");
        entryMovement.setGoods(validGoods);
        entryMovement.setCustomsStatus(CustomsStatus.X);
        entryMovement.setFromWarehouseCode("ORY01");
        entryMovement.setFromWarehouseLabel("Orly Cargo");

        exitMovement = new ExitMovement();
        exitMovement.setId(2L);
        exitMovement.setMovementTime(LocalDateTime.now());
        exitMovement.setCreatedBy("test-user");
        exitMovement.setGoods(validGoods);
        exitMovement.setCustomsStatus(CustomsStatus.Y);
        exitMovement.setToWarehouseCode("CDG02");
        exitMovement.setToWarehouseLabel("CDG Terminal 2");
        exitMovement.setCustomsDocumentType("T1");
        exitMovement.setCustomsDocumentRef("T1-YYYY-XXX");
    }

    @Test
    @DisplayName("Création d'une sortie sans entrée préalable doit échouer")
    void createExitMovement_WithoutPriorEntry_ShouldFail() {
        // Given
        when(validator.hasEntryMovement("12345678901")).thenReturn(false);

        // When & Then
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> movementService.createExitMovement(exitMovement)
        );

        assertEquals("Pas d'entrée trouvée pour 12345678901", exception.getMessage());
        verify(repository, never()).save(any());
    }


    @Test
    @DisplayName("Récupération d'un mouvement par ID inexistant doit échouer")
    void getMovementById_WithNonExistentId_ShouldFail() {
        // Given
        Long movementId = 999L;
        when(repository.findById(movementId)).thenReturn(Optional.empty());

        // When & Then
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> movementService.getMovementById(movementId)
        );

        assertEquals("Mouvement 999 introuvable", exception.getMessage());
    }
}
