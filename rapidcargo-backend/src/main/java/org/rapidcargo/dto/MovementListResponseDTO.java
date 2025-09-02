package org.rapidcargo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class MovementListResponseDTO {
    @JsonProperty("movements")
    private List<MovementResponseDTO> movements;

    @JsonProperty("totalCount")
    private int totalCount;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    public MovementListResponseDTO() {
        this.timestamp = LocalDateTime.now();
    }

    public MovementListResponseDTO(List<MovementResponseDTO> movements, int totalCount) {
        this();
        this.movements = movements;
        this.totalCount = totalCount;
    }

    public List<MovementResponseDTO> getMovements() {
        return movements;
    }

    public void setMovements(List<MovementResponseDTO> movements) {
        this.movements = movements;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MovementListResponseDTO{" +
                "movements=" + movements +
                ", totalCount=" + totalCount +
                ", timestamp=" + timestamp +
                '}';
    }
}
