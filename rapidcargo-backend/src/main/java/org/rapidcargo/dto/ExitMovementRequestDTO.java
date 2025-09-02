package org.rapidcargo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.rapidcargo.domain.enums.CustomsStatus;

import java.time.LocalDateTime;

public class ExitMovementRequestDTO {
    @NotBlank(message = "Le code de l'entrepôt d'origine est obligatoire")
    @Size(max = 20, message = "Le code de l'entrepôt d'origine ne peut pas dépasser 20 caractères")
    @JsonProperty("fromWarehouseCode")
    private String fromWarehouseCode;

    @NotBlank(message = "Le libellé de l'entrepôt d'origine est obligatoire")
    @Size(max = 100, message = "Le libellé de l'entrepôt d'origine ne peut pas dépasser 100 caractères")
    @JsonProperty("fromWarehouseLabel")
    private String fromWarehouseLabel;

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

    @NotBlank(message = "L'utilisateur créateur est obligatoire")
    @Size(max = 50, message = "L'utilisateur créateur ne peut pas dépasser 50 caractères")
    @JsonProperty("createdBy")
    private String createdBy;

    public ExitMovementRequestDTO() {}

    public ExitMovementRequestDTO(String fromWarehouseCode, String fromWarehouseLabel,
                                  GoodsDTO goods, LocalDateTime movementTime,
                                  CustomsStatus customsStatus, String createdBy) {
        this.fromWarehouseCode = fromWarehouseCode;
        this.fromWarehouseLabel = fromWarehouseLabel;
        this.goods = goods;
        this.movementTime = movementTime;
        this.customsStatus = customsStatus;
        this.createdBy = createdBy;
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

    public void setFromWarehouseLabel(String fromWarehouseLabel) {
        this.fromWarehouseLabel = fromWarehouseLabel;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "ExitMovementRequestDTO{" +
                "fromWarehouseCode='" + fromWarehouseCode + '\'' +
                ", fromWarehouseLabel='" + fromWarehouseLabel + '\'' +
                ", goods=" + goods +
                ", movementTime=" + movementTime +
                ", customsStatus=" + customsStatus +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
