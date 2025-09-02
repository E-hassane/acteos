package org.rapidcargo.domain;

import org.rapidcargo.domain.enums.CustomsStatus;
import org.rapidcargo.domain.enums.MovementType;
import org.rapidcargo.domain.exception.BusinessException;

import java.time.LocalDateTime;

public abstract class Movement {
    private Long id;
    private LocalDateTime movementTime;
    private String createdBy;
    private LocalDateTime createdAt;

    private final String declaredInCode = "CDGRC1";
    private final String getDeclaredInLabel = "RapidCargo CDG";

    private Goods goods;
    private CustomsStatus customsStatus;

    public Movement() {
        this.createdAt = LocalDateTime.now();
    }

    public Movement(Long id, LocalDateTime movementTime, String createdBy, LocalDateTime createdAt) {
        this();
        this.id = id;
        this.movementTime = movementTime;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public abstract MovementType getType();

    public void validate() {
        validateCommonFields();
        if (goods != null) {
            goods.validate();
        }
        validateSpecificFields();
    }

    protected abstract void validateSpecificFields();

    private void validateCommonFields() {
        if (movementTime == null) {
            throw new BusinessException("La date/heure du mouvement est obligatoire");
        }
        if (createdBy == null || createdBy.trim().isEmpty()) {
            throw new BusinessException("L'utilisateur créateur est obligatoire");
        }
        if (goods == null) {
            throw new BusinessException("Les informations de marchandise sont obligatoires");
        }
        if (customsStatus == null) {
            throw new BusinessException("Le statut douanier est obligatoire");
        }
    }

}
