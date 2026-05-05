package org.example.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
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

    public DlcDto save(DlcDto dto) {
        if (dto.getId() == null || dto.getId().isEmpty()) {
            dto.setId(UUID.randomUUID().toString());
        }
        Dlc dlc = dlcMapper.toDomain(dto);
        return dlcMapper.toDto(dlcRepository.save(dlc));
    }

    public Optional<DlcDto> findById(String id) {
        return dlcRepository.findById(id).map(dlcMapper::toDto);
    }

    public List<DlcDto> findAll() {
        return dlcRepository.findAll().stream()
                .map(dlcMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<DlcDto> update(String id, DlcDto dto) {
        Optional<Dlc> found = dlcRepository.findById(id);
        if (found.isEmpty()) {
            return Optional.empty();
        }
        Dlc existing = found.get();
        existing.setDlcName(dto.getDlcName());
        existing.setGameTitle(dto.getGameTitle());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setReleaseDate(dto.getReleaseDate());
        return Optional.of(dlcMapper.toDto(dlcRepository.update(existing)));
    }

    public void delete(String id) {
        dlcRepository.deleteById(id);
    }
}
