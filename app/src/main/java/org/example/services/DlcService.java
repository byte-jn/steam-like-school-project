package org.example.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.example.dtos.DlcDto;
import org.example.entities.Dlc;
import org.example.mappers.DlcMapper;
import org.example.repositories.DlcRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class DlcService {

    private final DlcRepository dlcRepository;
    private final DlcMapper dlcMapper;

    @Inject
    public DlcService(DlcRepository dlcRepository, DlcMapper dlcMapper) {
        this.dlcRepository = dlcRepository;
        this.dlcMapper = dlcMapper;
    }

    /**
     * Persists a new DLC from the given DTO.
     * Generates a UUID if the DTO carries no id.
     */
    public DlcDto save(DlcDto dto) {
        if (dto.getId() == null || dto.getId().isEmpty()) {
            dto.setId(UUID.randomUUID().toString());
        }
        Dlc dlc = dlcMapper.toDomain(dto);
        dlcRepository.save(dlc);
        return dlcMapper.toDto(dlc);
    }

    /**
     * Returns a DLC DTO by id, or empty if not found.
     */
    public Optional<DlcDto> findById(String id) {
        return dlcRepository.findById(id)
                .map(dlcMapper::toDto);
    }

    /**
     * Returns all DLCs as DTOs.
     */
    public List<DlcDto> findAll() {
        return dlcRepository.findAll()
                .stream()
                .map(dlcMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing DLC from the given DTO.
     */
    public void update(DlcDto dto) {
        Dlc dlc = dlcMapper.toDomain(dto);
        dlcRepository.update(dlc);
    }

    /**
     * Deletes a DLC by id.
     */
    public void delete(String id) {
        dlcRepository.delete(id);
    }
}
