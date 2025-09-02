package org.rapidcargo.service;

import org.rapidcargo.domain.EntryMovement;
import org.rapidcargo.domain.ExitMovement;
import org.rapidcargo.domain.Goods;
import org.rapidcargo.domain.Movement;
import org.rapidcargo.domain.exception.BusinessException;
import org.rapidcargo.mapper.EntityMapper;
import org.rapidcargo.repository.MovementRepository;
import org.rapidcargo.repository.entity.MovementEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovementService {

    private static final Logger logger = LoggerFactory.getLogger(MovementService.class);

    private final MovementRepository repository;
    private final MovementValidationService validator;
    private final XmlGeneratorService xmlGenerator;
    private final EmailService emailService;
    private final EntityMapper entityMapper;

    public MovementService(MovementRepository repository,
                           MovementValidationService validator,
                           XmlGeneratorService xmlGenerator,
                           EmailService emailService,
                           EntityMapper entityMapper) {
        this.repository = repository;
        this.validator = validator;
        this.xmlGenerator = xmlGenerator;
        this.emailService = emailService;
        this.entityMapper = entityMapper;
    }

    public EntryMovement createEntryMovement(String fromWarehouseCode, String fromWarehouseLabel,
                                             Goods goods, LocalDateTime movementTime, String createdBy) {

        logger.info("Création entrée pour AWB: {}", goods.getReferenceCode());

        EntryMovement movement = new EntryMovement();
        movement.setFromWarehouseCode(fromWarehouseCode);
        movement.setFromWarehouseLabel(fromWarehouseLabel);
        movement.setGoods(goods);
        movement.setMovementTime(movementTime);
        movement.setCreatedBy(createdBy);

        validator.validateMovementConsistency(movement);

        Movement saved = saveMovement(movement);
        sendNotificationAsync(saved);

        return (EntryMovement) saved;
    }

    public ExitMovement createExitMovement(String toWarehouseCode, String toWarehouseLabel,
                                           Goods goods, LocalDateTime movementTime, String createdBy,
                                           String customsDocumentType, String customsDocumentRef) {

        logger.info("Création sortie pour AWB: {}", goods.getReferenceCode());

        if (!validator.hasEntryMovement(goods.getReferenceCode())) {
            throw new BusinessException("Pas d'entrée trouvée pour " + goods.getReferenceCode());
        }

        ExitMovement movement = new ExitMovement();
        movement.setToWarehouseCode(toWarehouseCode);
        movement.setToWarehouseLabel(toWarehouseLabel);
        movement.setGoods(goods);
        movement.setMovementTime(movementTime);
        movement.setCreatedBy(createdBy);
        movement.setCustomsDocumentType(customsDocumentType);
        movement.setCustomsDocumentRef(customsDocumentRef);

        validator.validateMovementConsistency(movement);

        Movement saved = saveMovement(movement);
        sendNotificationAsync(saved);

        return (ExitMovement) saved;
    }

    @Transactional(readOnly = true)
    public List<Movement> getLatestMovements() {
        PageRequest page = PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt"));

        return repository.findAll(page)
                .getContent()
                .stream()
                .map(entityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Movement getMovementById(Long id) {
        MovementEntity entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Mouvement " + id + " introuvable"));

        return entityMapper.toDomain(entity);
    }

    @Transactional(readOnly = true)
    public List<Movement> getMovementsByReference(String referenceCode) {
        return repository.findByReferenceCode(referenceCode)
                .stream()
                .map(entityMapper::toDomain)
                .collect(Collectors.toList());
    }

    private Movement saveMovement(Movement movement) {
        try {
            MovementEntity entity = entityMapper.toEntity(movement);
            MovementEntity saved = repository.save(entity);
            return entityMapper.toDomain(saved);
        } catch (Exception e) {
            logger.error("Erreur sauvegarde: {}", e.getMessage());
            throw new BusinessException("Échec sauvegarde mouvement", e);
        }
    }

    private void sendNotificationAsync(Movement movement) {
        try {
            String xml = xmlGenerator.generateCargoMessage(movement);
            emailService.sendMovementNotification(movement, xml);
            logger.debug("Notification envoyée pour mouvement {}", movement.getId());
        } catch (Exception e) {
            logger.warn("Échec notification mouvement {}: {}", movement.getId(), e.getMessage());
        }
    }
}
