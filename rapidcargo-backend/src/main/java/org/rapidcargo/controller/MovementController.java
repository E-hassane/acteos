package org.rapidcargo.controller;


import jakarta.validation.Valid;
import org.rapidcargo.domain.EntryMovement;
import org.rapidcargo.domain.ExitMovement;
import org.rapidcargo.domain.Movement;
import org.rapidcargo.dto.EntryMovementRequestDTO;
import org.rapidcargo.dto.ExitMovementRequestDTO;
import org.rapidcargo.dto.MovementListResponseDTO;
import org.rapidcargo.dto.MovementResponseDTO;
import org.rapidcargo.mapper.MovementMapper;
import org.rapidcargo.service.MovementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movements")
public class MovementController {

    private static final Logger logger = LoggerFactory.getLogger(MovementController.class);

    private final MovementService movementService;
    private final MovementMapper mapper;

    public MovementController(MovementService movementService, MovementMapper mapper) {
        this.movementService = movementService;
        this.mapper = mapper;
    }

    @PostMapping("/entry")
    public ResponseEntity<MovementResponseDTO> createEntry(@Valid @RequestBody EntryMovementRequestDTO request) {
        logger.info("Création mouvement entrée pour AWB: {}", request.getGoods().getReferenceCode());

        EntryMovement movement = mapper.toEntryMovement(request);
        EntryMovement created = movementService.createEntryMovement(movement);

        MovementResponseDTO response = mapper.toMovementResponseDTO(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/exit")
    public ResponseEntity<MovementResponseDTO> createExit(@Valid @RequestBody ExitMovementRequestDTO request) {
        logger.info("Création mouvement sortie pour AWB: {}", request.getGoods().getReferenceCode());

        ExitMovement movement = mapper.toExitMovement(request);
        ExitMovement created = movementService.createExitMovement(movement);


        MovementResponseDTO response = mapper.toMovementResponseDTO(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/latest")
    public ResponseEntity<MovementListResponseDTO> getLatest() {
        List<Movement> movements = movementService.getLatestMovements();
        List<MovementResponseDTO> dtos = mapper.toMovementResponseDTOList(movements);

        MovementListResponseDTO response = new MovementListResponseDTO(dtos, dtos.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovementResponseDTO> getById(@PathVariable Long id) {
        Movement movement = movementService.getMovementById(id);
        MovementResponseDTO response = mapper.toMovementResponseDTO(movement);
        return ResponseEntity.ok(response);
    }
}
