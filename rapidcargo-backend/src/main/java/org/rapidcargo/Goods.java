package org.rapidcargo;

import org.rapidcargo.enums.ReferenceType;
import org.rapidcargo.exception.BusinessException;

import java.math.BigDecimal;

public class Goods {
    private ReferenceType referenceType;
    private String referenceCode;
    private Integer quantity;
    private BigDecimal weight;
    private Integer totalRefQuantity;
    private BigDecimal totalRefWeight;
    private String description;

    public Goods() {
    }

    public Goods(ReferenceType referenceType, String referenceCode,
                 Integer quantity, BigDecimal weight,
                 Integer totalRefQuantity, BigDecimal totalRefWeight,
                 String description) {
        this.referenceType = referenceType;
        this.referenceCode = referenceCode;
        this.quantity = quantity;
        this.weight = weight;
        this.totalRefQuantity = totalRefQuantity;
        this.totalRefWeight = totalRefWeight;
        this.description = description;
    }

    public void validate() {
        validateRequiredFields();
        validateAwbFormat();
        validateQuantityAndWeight();
    }

    private void validateRequiredFields() {
        if (referenceType == null) {
            throw new BusinessException("Le type de référence est obligatoire");
        }
        if (referenceCode == null || referenceCode.trim().isEmpty()) {
            throw new BusinessException("Le code de référence est obligatoire");
        }
        if (quantity == null || quantity <= 0) {
            throw new BusinessException("La quantité doit être positive");
        }
        if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Le poids doit être positif");
        }
        if (totalRefQuantity == null || totalRefQuantity <= 0) {
            throw new BusinessException("La quantité totale de référence doit être positive");
        }
        if (totalRefWeight == null || totalRefWeight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Le poids total de référence doit être positif");
        }
    }

    private void validateAwbFormat() {
        if (referenceType == ReferenceType.AWB) {
            if (!referenceCode.matches("\\d{11}")) {
                throw new BusinessException("La référence AWB doit contenir exactement 11 chiffres");
            }
        }
    }

    private void validateQuantityAndWeight() {
        if (totalRefQuantity < quantity) {
            throw new BusinessException("La quantité totale de référence doit être >= à la quantité du mouvement");
        }
        if (totalRefWeight.compareTo(weight) < 0) {
            throw new BusinessException("Le poids total de référence doit être >= au poids du mouvement");
        }
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getTotalRefQuantity() {
        return totalRefQuantity;
    }

    public void setTotalRefQuantity(Integer totalRefQuantity) {
        this.totalRefQuantity = totalRefQuantity;
    }

    public BigDecimal getTotalRefWeight() {
        return totalRefWeight;
    }

    public void setTotalRefWeight(BigDecimal totalRefWeight) {
        this.totalRefWeight = totalRefWeight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
