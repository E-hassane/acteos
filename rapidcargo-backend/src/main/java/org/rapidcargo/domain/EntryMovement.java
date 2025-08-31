package org.rapidcargo.domain;

import org.rapidcargo.domain.enums.MovementType;

import java.time.LocalDateTime;

public class EntryMovement extends Movement {
    private String fromWarehouseCode;
    private String fromWarehouseLabel;

    public EntryMovement() {
        super();
    }

    public EntryMovement(Long id, LocalDateTime movementTime,
                         String createdBy, LocalDateTime createdAt,
                         String fromWarehouseCode, String fromWarehouseLabel) {
        super(id, movementTime, createdBy, createdAt);
        this.fromWarehouseCode = fromWarehouseCode;
        this.fromWarehouseLabel = fromWarehouseLabel;
    }

    @Override
    public MovementType getType() {
        return null;
    }

    public String getFromWarehouseCode() {
        return fromWarehouseCode;
    }
    public void setFromWarehouseCode(String fromWarehouseCode) {
        this.fromWarehouseCode = fromWarehouseCode;
    }

    public String getFromWarehouseLabel() {
        return fromWarehouseLabel;
    }
    public void setFromWarehouseLabel(String fromWarehouseLabel) { this.fromWarehouseLabel = fromWarehouseLabel; }
}
