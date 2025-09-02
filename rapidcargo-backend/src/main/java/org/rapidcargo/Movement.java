package org.rapidcargo;

import org.rapidcargo.enums.CustomsStatus;
import org.rapidcargo.enums.MovementType;
import org.rapidcargo.exception.BusinessException;

import java.time.LocalDateTime;

public abstract class Movement {
    private Long id;
    private LocalDateTime movementTime;
    private String createdBy;
    private LocalDateTime createdAt;

    private final String declaredInCode = "CDGRC1";
    private final String declaredInLabel = "RapidCargo CDG";

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getMovementTime() {
        return movementTime;
    }

    public void setMovementTime(LocalDateTime movementTime) {
        this.movementTime = movementTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeclaredInCode() {
        return declaredInCode;
    }

    public String getDeclaredInLabel() {
        return declaredInLabel;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public CustomsStatus getCustomsStatus() {
        return customsStatus;
    }

    public void setCustomsStatus(CustomsStatus customsStatus) {
        this.customsStatus = customsStatus;
    }
}
