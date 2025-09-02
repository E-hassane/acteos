package org.rapidcargo.domain;

import org.rapidcargo.domain.enums.MovementType;
import org.rapidcargo.domain.exception.BusinessException;


public class ExitMovement extends Movement {
    private String toWarehouseCode;
    private String toWarehouseLabel;
    private String customsDocumentType;
    private String customsDocumentRef;

    public ExitMovement() {
        super();
    }

    @Override
    public MovementType getType() {
        return MovementType.EXIT;
    }

    @Override
    protected void validateSpecificFields() {
        if (toWarehouseCode == null || toWarehouseCode.trim().isEmpty()) {
            throw new BusinessException("Le code de l'entrepôt de destination est obligatoire pour une sortie");
        }
        if (toWarehouseLabel == null || toWarehouseLabel.trim().isEmpty()) {
            throw new BusinessException("Le libellé de l'entrepôt de destination est obligatoire pour une sortie");
        }
        if (customsDocumentType == null || customsDocumentType.trim().isEmpty()) {
            throw new BusinessException("Le type de document douanier est obligatoire pour une sortie");
        }
        if (customsDocumentRef == null || customsDocumentRef.trim().isEmpty()) {
            throw new BusinessException("La référence du document douanier est obligatoire pour une sortie");
        }
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
