package org.rapidcargo.service;

import org.rapidcargo.domain.EntryMovement;
import org.rapidcargo.domain.ExitMovement;
import org.rapidcargo.domain.Goods;
import org.rapidcargo.domain.Movement;
import org.rapidcargo.domain.exception.BusinessException;
import org.rapidcargo.mapper.EntityMapper;
import org.rapidcargo.repository.MovementRepository;
import org.rapidcargo.repository.entity.MovementEntity;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MovementService {
    private static final Logger logger = LoggerFactory.getLogger(MovementService.class);
    private static final int DEFAULT_LATEST_MOVEMENTS_LIMIT = 50;

    private final MovementRepository movementRepository;
    private final MovementValidationService validationService;
    private final EntityMapper entityMapper;

    private final XmlGeneratorService xmlGeneratorService;
    private final EmailService emailService;

    @Autowired
    public MovementService(MovementRepository movementRepository,
                           MovementValidationService validationService,
                           XmlGeneratorService xmlGeneratorService,
                           EmailService emailService,
                           EntityMapper entityMapper) {
        this.movementRepository = movementRepository;
        this.validationService = validationService;
        this.xmlGeneratorService = xmlGeneratorService;
        this.emailService = emailService;
        this.entityMapper = entityMapper;
    }

    /**
     *
     * @param fromWarehouseCode
     * @param fromWarehouseLabel
     * @param goods
     * @param movementTime
     * @param createdBy
     * @return
     */
    public EntryMovement createEntryMovement(String fromWarehouseCode,
                                             String fromWarehouseLabel,
                                             Goods goods,
                                             LocalDateTime movementTime,
                                             String createdBy) {

        logger.info("Création d'un mouvement d'entrée pour la référence {}", goods.getReferenceCode());

        EntryMovement movement = new EntryMovement();
        movement.setFromWarehouseCode(fromWarehouseCode);
        movement.setFromWarehouseLabel(fromWarehouseLabel);
        movement.setGoods(goods);
        movement.setMovementTime(movementTime);
        movement.setCreatedBy(createdBy);

        validationService.validateMovementConsistency(movement);

        Movement savedMovement = saveMovement(movement);

        sendNotificationAsync(savedMovement);


        logger.info("Mouvement d'entrée créé avec succès - ID: {}", savedMovement.getId());
        return (EntryMovement) savedMovement;
    }

    /**
     *
     * @param toWarehouseCode
     * @param toWarehouseLabel
     * @param goods
     * @param movementTime
     * @param createdBy
     * @param customsDocumentType
     * @param customsDocumentRef
     * @return
     */
    public ExitMovement createExitMovement(String toWarehouseCode,
                                           String toWarehouseLabel,
                                           Goods goods,
                                           LocalDateTime movementTime,
                                           String createdBy,
                                           String customsDocumentType,
                                           String customsDocumentRef) {

        logger.info("Création d'un mouvement de sortie pour la référence {}", goods.getReferenceCode());

        // Vérification de l'existence d'une entrée préalable
        if (!validationService.hasEntryMovement(goods.getReferenceCode())) {
            throw new BusinessException(
                    String.format("Impossible de créer une sortie : aucune entrée trouvée pour la référence %s",
                            goods.getReferenceCode())
            );
        }

        ExitMovement movement = new ExitMovement();
        movement.setToWarehouseCode(toWarehouseCode);
        movement.setToWarehouseLabel(toWarehouseLabel);
        movement.setGoods(goods);
        movement.setMovementTime(movementTime);
        movement.setCreatedBy(createdBy);
        movement.setCustomsDocumentType(customsDocumentType);
        movement.setCustomsDocumentRef(customsDocumentRef);

        validationService.validateMovementConsistency(movement);

        Movement savedMovement = saveMovement(movement);

        sendNotificationAsync(savedMovement);

        logger.info("Mouvement de sortie créé avec succès - ID: {}", savedMovement.getId());
        return (ExitMovement) savedMovement;
    }

    /**
     *
     * @param limit
     * @return
     */
    @Transactional(readOnly = true)
    public List<Movement> getLatestMovements(Integer limit) {
        int effectiveLimit = (limit != null && limit > 0) ? limit : DEFAULT_LATEST_MOVEMENTS_LIMIT;

        logger.debug("Récupération des {} derniers mouvements", effectiveLimit);

        PageRequest pageRequest = PageRequest.of(0, effectiveLimit,
                Sort.by(Sort.Direction.DESC, "createdAt"));

        return movementRepository.findAll(pageRequest)
                .getContent()
                .stream()
                .map(entityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param referenceCode
     * @return
     */
    @Transactional(readOnly = true)
    public List<Movement> findMovementsByReference(String referenceCode) {
        if (referenceCode == null || referenceCode.trim().isEmpty()) {
            throw new BusinessException("Le code de référence ne peut pas être vide");
        }

        logger.debug("Recherche des mouvements pour la référence {}", referenceCode);

        return movementRepository.findByReferenceCode(referenceCode)
                .stream()
                .map(entityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param movement
     * @return
     */
    private Movement saveMovement(Movement movement) {
        try {
            MovementEntity entity = entityMapper.toEntity(movement);
            MovementEntity savedEntity = movementRepository.save(entity);
            return entityMapper.toDomain(savedEntity);
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde du mouvement: {}", e.getMessage(), e);
            throw new BusinessException("Échec de la sauvegarde du mouvement: " + e.getMessage(), e);
        }
    }

    private void sendNotificationAsync(Movement movement) {
        try {
            String xmlContent = xmlGeneratorService.generateCargoMessage(movement);
            emailService.sendMovementNotification(movement, xmlContent);
        } catch (Exception e) {
            logger.error("Erreur lors de l'envoi de la notification pour le mouvement {}: {}",
                    movement.getId(), e.getMessage(), e);
        }
    }
}
