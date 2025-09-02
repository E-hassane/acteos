package org.rapidcargo.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.rapidcargo.EntryMovement;
import org.rapidcargo.ExitMovement;
import org.rapidcargo.Goods;
import org.rapidcargo.enums.CustomsStatus;
import org.rapidcargo.enums.ReferenceType;
import org.rapidcargo.exception.BusinessException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MovementTest {

    private EntryMovement entryMovement;
    private ExitMovement exitMovement;
    private Goods validGoods;

    @BeforeEach
    void setUp() {
        validGoods = new Goods(
                ReferenceType.AWB,
                "12345678901",
                10,
                new BigDecimal("15.500"),
                100,
                new BigDecimal("155.000"),
                "Marchandise test"
        );

        entryMovement = new EntryMovement();
        entryMovement.setMovementTime(LocalDateTime.now());
        entryMovement.setCreatedBy("test-user");
        entryMovement.setGoods(validGoods);
        entryMovement.setCustomsStatus(CustomsStatus.X);
        entryMovement.setFromWarehouseCode("ORY01");
        entryMovement.setFromWarehouseLabel("Orly Cargo");

        exitMovement = new ExitMovement();
        exitMovement.setMovementTime(LocalDateTime.now());
        exitMovement.setCreatedBy("test-user");
        exitMovement.setGoods(validGoods);
        exitMovement.setCustomsStatus(CustomsStatus.Y);
        exitMovement.setToWarehouseCode("CDG02");
        exitMovement.setToWarehouseLabel("CDG Terminal 2");
        exitMovement.setCustomsDocumentType("T1");
        exitMovement.setCustomsDocumentRef("T1-2025-001");
    }

    @Test
    @DisplayName("Un mouvement d'entrée valide doit passer la validation")
    void validEntryMovement_ShouldPassValidation() {
        assertDoesNotThrow(() -> entryMovement.validate());
        assertEquals("CDGRC1", entryMovement.getDeclaredInCode());
        assertEquals("RapidCargo CDG", entryMovement.getDeclaredInLabel());
    }

    @Test
    @DisplayName("Un mouvement de sortie valide doit passer la validation")
    void validExitMovement_ShouldPassValidation() {
        assertDoesNotThrow(() -> exitMovement.validate());
    }

    @Test
    @DisplayName("Un mouvement d'entrée sans code d'entrepôt d'origine doit échouer")
    void entryMovement_WithoutFromWarehouseCode_ShouldFail() {
        entryMovement.setFromWarehouseCode(null);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> entryMovement.validate()
        );

        assertEquals("Le code de l'entrepôt d'origine est obligatoire pour une entrée",
                exception.getMessage());
    }

    @Test
    @DisplayName("Un mouvement de sortie sans document douanier doit échouer")
    void exitMovement_WithoutCustomsDocument_ShouldFail() {
        exitMovement.setCustomsDocumentRef(null);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> exitMovement.validate()
        );

        assertEquals("La référence du document douanier est obligatoire pour une sortie",
                exception.getMessage());
    }

    @Test
    @DisplayName("Un mouvement sans date doit échouer")
    void movement_WithoutMovementTime_ShouldFail() {
        entryMovement.setMovementTime(null);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> entryMovement.validate()
        );

        assertEquals("La date/heure du mouvement est obligatoire",
                exception.getMessage());
    }
}
