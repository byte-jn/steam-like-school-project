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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Singleton
public class DlcService {

    private static final Pattern UUID_PATTERN =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[A-Za-z0-9 \\-!:'.,&]{1,255}$");

    private final DlcRepository dlcRepository;
    private final DlcMapper dlcMapper;

    @Inject
    public DlcService(DlcRepository dlcRepository, DlcMapper dlcMapper) {
        this.dlcRepository = dlcRepository;
        this.dlcMapper = dlcMapper;
    }

    private void validate(DlcDto dto) {
        if (dto.getId() != null && !UUID_PATTERN.matcher(dto.getId()).matches()) {
            throw new IllegalArgumentException("Invalid id: must be a UUID");
        }
        if (dto.getDlcName() != null && !NAME_PATTERN.matcher(dto.getDlcName()).matches()) {
            throw new IllegalArgumentException(
                    "Invalid DLC name: letters, numbers, spaces and basic punctuation only (max 255)");
        }
        if (dto.getGameTitle() != null && !NAME_PATTERN.matcher(dto.getGameTitle()).matches()) {
            throw new IllegalArgumentException(
                    "Invalid game title: letters, numbers, spaces and basic punctuation only (max 255)");
        }
        if (dto.getPrice() < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
    }

    /**
     * Persists a new DLC from the given DTO.
     * Generates a UUID if the DTO carries no id.
     */
    public DlcDto save(DlcDto dto) {
        validate(dto);
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
     * Returns all DLCs for the given game title (case-insensitive).
     */
    public List<DlcDto> findByGameTitle(String gameTitle) {
        return dlcRepository.findByGameTitle(gameTitle)
                .stream()
                .map(dlcMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing DLC from the given DTO.
     */
    public void update(DlcDto dto) {
        validate(dto);
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
