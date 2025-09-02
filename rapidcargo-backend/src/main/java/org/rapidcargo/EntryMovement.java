package org.rapidcargo;

import org.rapidcargo.enums.MovementType;
import org.rapidcargo.exception.BusinessException;

public class EntryMovement extends Movement {
    private String fromWarehouseCode;
    private String fromWarehouseLabel;

    public EntryMovement() {
        super();
    }

    @Override
    public MovementType getType() {
        return MovementType.ENTRY;
    }

    @Override
    protected void validateSpecificFields() {
        if (fromWarehouseCode == null || fromWarehouseCode.trim().isEmpty()) {
            throw new BusinessException("Le code de l'entrepôt d'origine est obligatoire pour une entrée");
        }
        if (fromWarehouseLabel == null || fromWarehouseLabel.trim().isEmpty()) {
            throw new BusinessException("Le libellé de l'entrepôt d'origine est obligatoire pour une entrée");
        }
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
