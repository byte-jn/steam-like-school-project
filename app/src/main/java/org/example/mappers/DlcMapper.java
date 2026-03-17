package org.example.mappers;

import org.example.dtos.DlcDto;
import org.example.entities.Dlc;

public class DlcMapper {

    /**
     * Maps a Dlc domain entity to a DlcDto.
     */
    public DlcDto toDto(Dlc dlc) {
        DlcDto dto = new DlcDto();
        dto.setId(dlc.getId());
        dto.setDlcName(dlc.getDlcName());
        dto.setGameTitle(dlc.getGameTitle());
        dto.setDescription(dlc.getDescription());
        dto.setPrice(dlc.getPrice());
        dto.setReleaseDate(dlc.getReleaseDate());
        dto.setCreatedAt(dlc.getCreatedAt());
        return dto;
    }

    /**
     * Maps a DlcDto to a Dlc domain entity.
     * Uses the dto id when present, otherwise generate a new one at the call site.
     */
    public Dlc toDomain(DlcDto dto) {
        Dlc dlc = new Dlc(dto.getId());
        dlc.setDlcName(dto.getDlcName());
        dlc.setGameTitle(dto.getGameTitle());
        dlc.setDescription(dto.getDescription());
        dlc.setPrice(dto.getPrice());
        dlc.setReleaseDate(dto.getReleaseDate());
        return dlc;
    }
}
