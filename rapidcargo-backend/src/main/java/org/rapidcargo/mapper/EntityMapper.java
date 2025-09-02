package org.rapidcargo.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.rapidcargo.EntryMovement;
import org.rapidcargo.ExitMovement;
import org.rapidcargo.Goods;
import org.rapidcargo.Movement;
import org.rapidcargo.repository.entity.EntryMovementEntity;
import org.rapidcargo.repository.entity.ExitMovementEntity;
import org.rapidcargo.repository.entity.GoodsEntity;
import org.rapidcargo.repository.entity.MovementEntity;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    @Mapping(target = "referenceNumber", source = "goods.referenceCode")
    @Mapping(target = "referenceType", source = "goods.referenceType")
    @Mapping(target = "movementTime", expression = "java(movement.getMovementTime().toLocalDate())")
    EntryMovementEntity toEntity(EntryMovement movement);
    @Mapping(target = "referenceNumber", source = "goods.referenceCode")
    @Mapping(target = "referenceType", source = "goods.referenceType")
    @Mapping(target = "movementTime", expression = "java(movement.getMovementTime().toLocalDate())")
    ExitMovementEntity toEntity(ExitMovement movement);
    @Mapping(target = "movement", ignore = true)
    GoodsEntity toEntity(Goods goods);
    @Mapping(target = "movementTime", expression = "java(entity.getMovementTime().atStartOfDay())")
    EntryMovement toDomain(EntryMovementEntity entity);
    @Mapping(target = "movementTime", expression = "java(entity.getMovementTime().atStartOfDay())")
    ExitMovement toDomain(ExitMovementEntity entity);
    Goods toDomain(GoodsEntity entity);

    default MovementEntity toEntity(Movement movement) {
        return switch (movement.getType()) {
            case ENTRY -> toEntity((EntryMovement) movement);
            case EXIT -> toEntity((ExitMovement) movement);
        };
    }

    default Movement toDomain(MovementEntity entity) {
        return switch (entity.getClass().getSimpleName()) {
            case "EntryMovementEntity" -> toDomain((EntryMovementEntity) entity);
            case "ExitMovementEntity" -> toDomain((ExitMovementEntity) entity);
            default -> throw new IllegalArgumentException("Type entité inconnu: " + entity.getClass());
        };
    }
}

