package org.rapidcargo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.rapidcargo.enums.ReferenceType;

import java.math.BigDecimal;

public class GoodsDTO {

    @NotNull(message = "Le type de référence est obligatoire")
    @JsonProperty("referenceType")
    private ReferenceType referenceType;

    @NotBlank(message = "Le code de référence est obligatoire")
    @Size(max = 50, message = "Le code de référence ne peut pas dépasser 50 caractères")
    @JsonProperty("referenceCode")
    private String referenceCode;

    @NotNull(message = "La quantité est obligatoire")
    @Positive(message = "La quantité doit être positive")
    @JsonProperty("quantity")
    private Integer quantity;

    @NotNull(message = "Le poids est obligatoire")
    @Positive(message = "La quantité totale de référence doit être positive")
    @Digits(integer = 7, fraction = 3, message = "Le poids doit avoir au maximum 7 chiffres avant la virgule et 3 après")
    @JsonProperty("weight")
    private BigDecimal weight;

    @NotNull(message = "La quantité totale de référence est obligatoire")
    @Positive(message = "La quantité totale de référence doit être positive")
    @JsonProperty("totalRefQuantity")
    private Integer totalRefQuantity;

    @NotNull(message = "Le poids total de référence est obligatoire")
    @Positive(message = "La quantité totale de référence doit être positive")
    @Digits(integer = 7, fraction = 3, message = "Le poids total de référence doit avoir au maximum 7 chiffres avant la virgule et 3 après")
    @JsonProperty("totalRefWeight")
    private BigDecimal totalRefWeight;

    @Size(max = 255, message = "La description ne peut pas dépasser 255 caractères")
    @JsonProperty("description")
    private String description;

    public GoodsDTO() {}

    public GoodsDTO(ReferenceType referenceType, String referenceCode,
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

    @Override
    public String toString() {
        return "GoodsDTO{" +
                "referenceType=" + referenceType +
                ", referenceCode='" + referenceCode + '\'' +
                ", quantity=" + quantity +
                ", weight=" + weight +
                ", totalRefQuantity=" + totalRefQuantity +
                ", totalRefWeight=" + totalRefWeight +
                ", description='" + description + '\'' +
                '}';
    }
}
