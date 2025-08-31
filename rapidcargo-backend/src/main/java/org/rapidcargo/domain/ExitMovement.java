package org.rapidcargo.domain;

import org.rapidcargo.domain.enums.MovementType;

import java.time.LocalDateTime;

public class ExitMovement extends Movement {
    private String toWarehouseCode;
    private String toWarehouseLabel;
    private String customsDocumentType;
    private String customsDocumentRef;

    public ExitMovement() {
        super();
    }

    public ExitMovement(Long id, LocalDateTime movementTime,
                        String createdBy, LocalDateTime createdAt,
                        String toWarehouseCode, String toWarehouseLabel,
                        String customsDocumentType, String customsDocumentRef) {
        super(id, movementTime, createdBy, createdAt);
        this.toWarehouseCode = toWarehouseCode;
        this.toWarehouseLabel = toWarehouseLabel;
        this.customsDocumentType = customsDocumentType;
        this.customsDocumentRef = customsDocumentRef;
    }
    @Override
    public MovementType getType() {
        return MovementType.EXIT;
    }

    public String getToWarehouseCode() {
        return toWarehouseCode;
    }
    public void setToWarehouseCode(String toWarehouseCode) {
        this.toWarehouseCode = toWarehouseCode;
    }

    public String getToWarehouseLabel() {
        return toWarehouseLabel;
    }
    public void setToWarehouseLabel(String toWarehouseLabel) {
        this.toWarehouseLabel = toWarehouseLabel;
    }

    public String getCustomsDocumentType() {
        return customsDocumentType;
    }
    public void setCustomsDocumentType(String customsDocumentType) {
        this.customsDocumentType = customsDocumentType;
    }

    public String getCustomsDocumentRef() {
        return customsDocumentRef;
    }
    public void setCustomsDocumentRef(String customsDocumentRef) {
        this.customsDocumentRef = customsDocumentRef;
    }
}
