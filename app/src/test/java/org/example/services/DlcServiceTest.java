package org.example.services;

import org.example.dtos.DlcDto;
import org.example.entities.Dlc;
import org.example.mappers.DlcMapper;
import org.example.repositories.DlcRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DlcServiceTest {

    @Mock
    private DlcRepository dlcRepository;

    @Mock
    private DlcMapper dlcMapper;

    private DlcService dlcService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dlcService = new DlcService(dlcRepository, dlcMapper);
    }

    @Test
    public void save_assignsUuidWhenIdIsNull() {
        DlcDto dto = new DlcDto();
        dto.setDlcName("Expansion");
        dto.setGameTitle("Witcher");
        dto.setPrice(19.99);

        Dlc dlc = new Dlc("generated");
        DlcDto returned = new DlcDto();
        returned.setId("generated");

        when(dlcMapper.toDomain(any())).thenReturn(dlc);
        when(dlcMapper.toDto(dlc)).thenReturn(returned);

        dlcService.save(dto);

        assertNotNull(dto.getId());
        verify(dlcRepository).save(dlc);
    }

    @Test
    public void save_keepsExistingValidId() {
        String existingId = "550e8400-e29b-41d4-a716-446655440000";
        DlcDto dto = new DlcDto();
        dto.setId(existingId);
        dto.setDlcName("Expansion");
        dto.setGameTitle("Witcher");
        dto.setPrice(19.99);

        Dlc dlc = new Dlc(existingId);
        when(dlcMapper.toDomain(any())).thenReturn(dlc);
        when(dlcMapper.toDto(dlc)).thenReturn(dto);

        dlcService.save(dto);

        assertEquals(existingId, dto.getId());
    }



    @Test
    public void findAll_returnsAllMappedDtos() {
        Dlc dlc = new Dlc("id-1");
        DlcDto dto = new DlcDto();
        dto.setId("id-1");

        when(dlcRepository.findAll()).thenReturn(Collections.singletonList(dlc));
        when(dlcMapper.toDto(dlc)).thenReturn(dto);

        List<DlcDto> result = dlcService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    public void findById_returnsMappedDto_whenFound() {
        Dlc dlc = new Dlc("id-1");
        DlcDto dto = new DlcDto();
        dto.setId("id-1");

        when(dlcRepository.findById("id-1")).thenReturn(Optional.of(dlc));
        when(dlcMapper.toDto(dlc)).thenReturn(dto);

        Optional<DlcDto> result = dlcService.findById("id-1");

        assertTrue(result.isPresent());
        assertEquals("id-1", result.get().getId());
    }

    @Test
    public void findById_returnsEmpty_whenNotFound() {
        when(dlcRepository.findById("missing")).thenReturn(Optional.empty());

        Optional<DlcDto> result = dlcService.findById("missing");

        assertFalse(result.isPresent());
    }

    @Test
    public void delete_delegatesToRepository() {
        dlcService.delete("some-id");

        verify(dlcRepository).deleteById("some-id");
    }
}
