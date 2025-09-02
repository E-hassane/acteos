package org.rapidcargo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.rapidcargo.enums.CustomsStatus;

import java.time.LocalDateTime;

public class ExitMovementRequestDTO {
    @NotBlank(message = "Le code de l'entrepôt de destination est obligatoire")
    @Size(max = 20, message = "Le code de l'entrepôt de destination ne peut pas dépasser 20 caractères")
    @JsonProperty("toWarehouseCode")
    private String toWarehouseCode;

    @NotBlank(message = "Le libellé de l'entrepôt de destination est obligatoire")
    @Size(max = 100, message = "Le libellé de l'entrepôt de destination ne peut pas dépasser 100 caractères")
    @JsonProperty("toWarehouseLabel")
    private String toWarehouseLabel;

    @NotNull(message = "Les informations de marchandise sont obligatoires")
    @Valid
    @JsonProperty("goods")
    private GoodsDTO goods;

    @NotNull(message = "La date/heure du mouvement est obligatoire")
    @JsonProperty("movementTime")
    private LocalDateTime movementTime;

    @NotNull(message = "Le statut douanier est obligatoire")
    @JsonProperty("customsStatus")
    private CustomsStatus customsStatus;

    @NotBlank(message = "Le type de document douanier est obligatoire")
    @JsonProperty("customsDocumentType")
    private String customsDocumentType;

    @NotBlank(message = "La référence du document douanier est obligatoire")
    @JsonProperty("customsDocumentRef")
    private String customsDocumentRef;

    @NotBlank(message = "L'utilisateur créateur est obligatoire")
    @JsonProperty("createdBy")
    private String createdBy;

    public ExitMovementRequestDTO() {}

    public ExitMovementRequestDTO(String toWarehouseCode, String toWarehouseLabel,
                                  GoodsDTO goods, LocalDateTime movementTime,
                                  CustomsStatus customsStatus, String customsDocumentType,
                                  String customsDocumentRef, String createdBy) {
        this.toWarehouseCode = toWarehouseCode;
        this.toWarehouseLabel = toWarehouseLabel;
        this.goods = goods;
        this.movementTime = movementTime;
        this.customsStatus = customsStatus;
        this.customsDocumentType = customsDocumentType;
        this.customsDocumentRef = customsDocumentRef;
        this.createdBy = createdBy;
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

    public GoodsDTO getGoods() {
        return goods;
    }

    public void setGoods(GoodsDTO goods) {
        this.goods = goods;
    }

    public LocalDateTime getMovementTime() {
        return movementTime;
    }

    public void setMovementTime(LocalDateTime movementTime) {
        this.movementTime = movementTime;
    }

    public CustomsStatus getCustomsStatus() {
        return customsStatus;
    }

    public void setCustomsStatus(CustomsStatus customsStatus) {
        this.customsStatus = customsStatus;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "ExitMovementRequestDTO{" +
                "toWarehouseCode='" + toWarehouseCode + '\'' +
                ", toWarehouseLabel='" + toWarehouseLabel + '\'' +
                ", goods=" + goods +
                ", movementTime=" + movementTime +
                ", customsStatus=" + customsStatus +
                ", customsDocumentType='" + customsDocumentType + '\'' +
                ", customsDocumentRef='" + customsDocumentRef + '\'' +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
