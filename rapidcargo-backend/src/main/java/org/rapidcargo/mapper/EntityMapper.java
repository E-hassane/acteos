package org.rapidcargo.mapper;

import org.rapidcargo.domain.EntryMovement;
import org.rapidcargo.domain.ExitMovement;
import org.rapidcargo.domain.Goods;
import org.rapidcargo.domain.Movement;
import org.rapidcargo.repository.entity.EntryMovementEntity;
import org.rapidcargo.repository.entity.ExitMovementEntity;
import org.rapidcargo.repository.entity.GoodsEntity;
import org.rapidcargo.repository.entity.MovementEntity;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    public MovementEntity toEntity(Movement domain) {
        if (domain == null) {
            return null;
        }

        MovementEntity entity = switch (domain.getType()) {
            case ENTRY -> mapEntryMovement((EntryMovement) domain);
            case EXIT -> mapExitMovement((ExitMovement) domain);
        };

        entity.setId(domain.getId());
        entity.setMovementTime(domain.getMovementTime().toLocalDate());
        entity.setCustomsStatus(domain.getCustomsStatus());
        entity.setGoods(toEntity(domain.getGoods()));

        return entity;
    }

    public Movement toDomain(MovementEntity entity) {
        if (entity == null) {
            return null;
        }

        Movement domain = switch (entity.getType()) {
            case ENTRY -> mapEntryMovementEntity((EntryMovementEntity) entity);
            case EXIT -> mapExitMovementEntity((ExitMovementEntity) entity);
        };

        domain.setId(entity.getId());
        domain.setMovementTime(entity.getMovementTime().atStartOfDay());
        domain.setCreatedBy(entity.getCreatedBy());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setCustomsStatus(entity.getCustomsStatus());
        domain.setGoods(toDomain(entity.getGoods()));

        return domain;
    }

    public GoodsEntity toEntity(Goods domain) {
        if (domain == null) {
            return null;
        }

        GoodsEntity entity = new GoodsEntity();
        entity.setReferenceType(domain.getReferenceType());
        entity.setReferenceCode(domain.getReferenceCode());
        entity.setQuantity(domain.getQuantity());
        entity.setWeight(domain.getWeight());
        entity.setTotalRefQuantity(domain.getTotalRefQuantity());
        entity.setTotalRefWeight(domain.getTotalRefWeight());
        entity.setDescription(domain.getDescription());

        return entity;
    }

    public Goods toDomain(GoodsEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Goods(
                entity.getReferenceType(),
                entity.getReferenceCode(),
                entity.getQuantity(),
                entity.getWeight(),
                entity.getTotalRefQuantity(),
                entity.getTotalRefWeight(),
                entity.getDescription()
        );
    }

    private EntryMovementEntity mapEntryMovement(EntryMovement domain) {
        EntryMovementEntity entity = new EntryMovementEntity();
        entity.setFromWarehouseCode(domain.getFromWarehouseCode());
        entity.setFromWarehouseLabel(domain.getFromWarehouseLabel());
        return entity;
    }

    private ExitMovementEntity mapExitMovement(ExitMovement domain) {
        ExitMovementEntity entity = new ExitMovementEntity();
        entity.setToWarehouseCode(domain.getToWarehouseCode());
        entity.setToWarehouseLabel(domain.getToWarehouseLabel());
        entity.setCustomsDocumentType(domain.getCustomsDocumentType());
        entity.setCustomsDocumentRef(domain.getCustomsDocumentRef());
        return entity;
    }

    private EntryMovement mapEntryMovementEntity(EntryMovementEntity entity) {
        return new EntryMovement(
                entity.getId(),
                entity.getMovementTime().atStartOfDay(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getFromWarehouseCode(),
                entity.getFromWarehouseLabel()
        );
    }

    private ExitMovement mapExitMovementEntity(ExitMovementEntity entity) {
        return new ExitMovement(
                entity.getId(),
                entity.getMovementTime().atStartOfDay(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getToWarehouseCode(),
                entity.getToWarehouseLabel(),
                entity.getCustomsDocumentType(),
                entity.getCustomsDocumentRef()
        );
    }
}
