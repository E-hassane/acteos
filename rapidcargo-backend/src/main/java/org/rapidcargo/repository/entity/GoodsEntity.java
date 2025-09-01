package org.rapidcargo.repository.entity;

import jakarta.persistence.*;
import org.rapidcargo.domain.enums.ReferenceType;

import java.math.BigDecimal;

@Entity
@Table(name = "goods")
public class GoodsEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type", nullable = false)
    private ReferenceType referenceType;

    @Column(name = "reference_code", nullable = false, length = 50)
    private String referenceCode;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "weight", nullable = false, precision = 10, scale = 3)
    private BigDecimal weight;

    @Column(name = "total_ref_quantity", nullable = false)
    private Integer totalRefQuantity;

    @Column(name = "total_ref_weight", nullable = false, precision = 10, scale = 3)
    private BigDecimal totalRefWeight;

    @Column(name = "description", length = 255)
    private String description;

    public GoodsEntity() {}

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
