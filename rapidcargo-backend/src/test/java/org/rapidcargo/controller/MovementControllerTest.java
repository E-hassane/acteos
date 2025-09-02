package org.rapidcargo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.rapidcargo.EntryMovement;
import org.rapidcargo.enums.CustomsStatus;
import org.rapidcargo.enums.MovementType;
import org.rapidcargo.enums.ReferenceType;
import org.rapidcargo.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.rapidcargo.dto.EntryMovementRequestDTO;
import org.rapidcargo.dto.GoodsDTO;
import org.rapidcargo.dto.MovementResponseDTO;
import org.rapidcargo.mapper.MovementMapper;
import org.rapidcargo.service.MovementService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovementController.class)
class MovementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovementService movementService;

    @MockBean
    private MovementMapper mapper;

    private ObjectMapper objectMapper;
    private EntryMovementRequestDTO entryRequest;
    private MovementResponseDTO movementResponse;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        GoodsDTO goodsDTO = new GoodsDTO(
                ReferenceType.AWB,
                "12345678901",
                10,
                new BigDecimal("15.500"),
                100,
                new BigDecimal("155.000"),
                "Test goods"
        );

        entryRequest = new EntryMovementRequestDTO(
                "ORY01",
                "Orly Cargo",
                goodsDTO,
                LocalDateTime.of(2025, 9, 3, 14, 30),
                CustomsStatus.X,
                "test-user"
        );

        movementResponse = new MovementResponseDTO();
        movementResponse.setId(1L);
        movementResponse.setType(MovementType.ENTRY);
    }

    @Test
    @DisplayName("POST /api/movements/entry avec données valides doit retourner 201")
    void createEntry_WithValidData_ShouldReturn201() throws Exception {
        EntryMovement entryMovement = new EntryMovement();
        entryMovement.setId(1L);

        when(mapper.toEntryMovement(any(EntryMovementRequestDTO.class))).thenReturn(entryMovement);
        when(movementService.createEntryMovement(any(EntryMovement.class))).thenReturn(entryMovement);
        when(mapper.toMovementResponseDTO(any(EntryMovement.class))).thenReturn(movementResponse);

        mockMvc.perform(post("/api/movements/entry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entryRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("ENTRY"));

        verify(movementService).createEntryMovement(any(EntryMovement.class));
    }

    @Test
    @DisplayName("POST /api/movements/entry avec données invalides doit retourner 400")
    void createEntry_WithInvalidData_ShouldReturn400() throws Exception {
        entryRequest.setFromWarehouseCode("");

        mockMvc.perform(post("/api/movements/entry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entryRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"));

        verify(movementService, never()).createEntryMovement(any());
    }

    @Test
    @DisplayName("GET /api/movements/latest doit retourner la liste des mouvements")
    void getLatest_ShouldReturnMovementsList() throws Exception {
        when(movementService.getLatestMovements()).thenReturn(java.util.List.of());
        when(mapper.toMovementResponseDTOList(any())).thenReturn(java.util.List.of());

        mockMvc.perform(get("/api/movements/latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movements").isArray())
                .andExpect(jsonPath("$.totalCount").exists())
                .andExpect(jsonPath("$.timestamp").exists());

        verify(movementService).getLatestMovements();
    }

    @Test
    @DisplayName("Erreur métier doit retourner 400 avec message approprié")
    void businessException_ShouldReturn400() throws Exception {
        when(mapper.toEntryMovement(any())).thenReturn(new EntryMovement());
        when(movementService.createEntryMovement(any()))
                .thenThrow(new BusinessException("Erreur métier test"));

        mockMvc.perform(post("/api/movements/entry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entryRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("BUSINESS_ERROR"))
                .andExpect(jsonPath("$.message").value("Erreur métier test"));
    }
}
