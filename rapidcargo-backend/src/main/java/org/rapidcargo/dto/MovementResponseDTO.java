package org.rapidcargo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.rapidcargo.enums.CustomsStatus;
import org.rapidcargo.enums.MovementType;

import java.time.LocalDateTime;

public class MovementResponseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("type")
    private MovementType type;

    @JsonProperty("movementTime")
    private LocalDateTime movementTime;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("declaredInCode")
    private String declaredInCode;

    @JsonProperty("declaredInLabel")
    private String declaredInLabel;

    @JsonProperty("goods")
    private GoodsDTO goods;

    @JsonProperty("customsStatus")
    private CustomsStatus customsStatus;

    @JsonProperty("fromWarehouseCode")
    private String fromWarehouseCode;

    @JsonProperty("fromWarehouseLabel")
    private String fromWarehouseLabel;

    @JsonProperty("toWarehouseCode")
    private String toWarehouseCode;

    @JsonProperty("toWarehouseLabel")
    private String toWarehouseLabel;

    @JsonProperty("customsDocumentType")
    private String customsDocumentType;

    @JsonProperty("customsDocumentRef")
    private String customsDocumentRef;

    public MovementResponseDTO() {}

    public MovementResponseDTO(Long id, MovementType type,
                               LocalDateTime movementTime, String createdBy,
                               LocalDateTime createdAt, String declaredInCode,
                               String declaredInLabel, GoodsDTO goods,
                               CustomsStatus customsStatus, String fromWarehouseCode,
                               String fromWarehouseLabel, String toWarehouseCode,
                               String toWarehouseLabel, String customsDocumentType,
                               String customsDocumentRef) {
        this.id = id;
        this.type = type;
        this.movementTime = movementTime;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.declaredInCode = declaredInCode;
        this.declaredInLabel = declaredInLabel;
        this.goods = goods;
        this.customsStatus = customsStatus;
        this.fromWarehouseCode = fromWarehouseCode;
        this.fromWarehouseLabel = fromWarehouseLabel;
        this.toWarehouseCode = toWarehouseCode;
        this.toWarehouseLabel = toWarehouseLabel;
        this.customsDocumentType = customsDocumentType;
        this.customsDocumentRef = customsDocumentRef;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
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

    public void setDeclaredInCode(String declaredInCode) {
        this.declaredInCode = declaredInCode;
    }

    public String getDeclaredInLabel() {
        return declaredInLabel;
    }

    public void setDeclaredInLabel(String declaredInLabel) {
        this.declaredInLabel = declaredInLabel;
    }

    public GoodsDTO getGoods() {
        return goods;
    }

    public void setGoods(GoodsDTO goods) {
        this.goods = goods;
    }

    public CustomsStatus getCustomsStatus() {
        return customsStatus;
    }

    public void setCustomsStatus(CustomsStatus customsStatus) {
        this.customsStatus = customsStatus;
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

    @Override
    public String toString() {
        return "MovementResponseDTO{" +
                "id=" + id +
                ", type=" + type +
                ", movementTime=" + movementTime +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt=" + createdAt +
                ", declaredInCode='" + declaredInCode + '\'' +
                ", declaredInLabel='" + declaredInLabel + '\'' +
                ", goods=" + goods +
                ", customsStatus=" + customsStatus +
                ", fromWarehouseCode='" + fromWarehouseCode + '\'' +
                ", fromWarehouseLabel='" + fromWarehouseLabel + '\'' +
                ", toWarehouseCode='" + toWarehouseCode + '\'' +
                ", toWarehouseLabel='" + toWarehouseLabel + '\'' +
                ", customsDocumentType='" + customsDocumentType + '\'' +
                ", customsDocumentRef='" + customsDocumentRef + '\'' +
                '}';
    }
}
