package org.rapidcargo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.rapidcargo.domain.EntryMovement;
import org.rapidcargo.domain.ExitMovement;
import org.rapidcargo.domain.Goods;
import org.rapidcargo.domain.Movement;
import org.rapidcargo.dto.EntryMovementRequestDTO;
import org.rapidcargo.dto.ExitMovementRequestDTO;
import org.rapidcargo.dto.GoodsDTO;
import org.rapidcargo.dto.MovementResponseDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovementMapper {

    Goods toGoods(GoodsDTO goodsDTO);
    GoodsDTO toGoodsDTO(Goods goods);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    EntryMovement toEntryMovement(EntryMovementRequestDTO requestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ExitMovement toExitMovement(ExitMovementRequestDTO requestDTO);

    MovementResponseDTO toMovementResponseDTO(Movement movement);
    MovementResponseDTO toMovementResponseDTO(EntryMovement movement);
    MovementResponseDTO toMovementResponseDTO(ExitMovement movement);

    List<MovementResponseDTO> toMovementResponseDTOList(List<Movement> movements);
}
