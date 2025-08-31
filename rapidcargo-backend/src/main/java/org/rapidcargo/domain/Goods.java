package org.rapidcargo.domain;

import org.rapidcargo.domain.enums.ReferenceType;

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
