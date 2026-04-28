package org.example.services;

import org.example.dtos.DlcDto;
import org.example.entities.Dlc;
import org.example.mappers.DlcMapper;
import org.example.repositories.DlcRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DlcServiceTest {

    @Mock
    private DlcRepository dlcRepository;

    @Mock
    private DlcMapper dlcMapper;

    private DlcService dlcService;

    @Before
    public void setUp() {
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

    @Test(expected = IllegalArgumentException.class)
    public void save_throwsOnNegativePrice() {
        DlcDto dto = new DlcDto();
        dto.setDlcName("Expansion");
        dto.setGameTitle("Witcher");
        dto.setPrice(-5.0);

        dlcService.save(dto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_throwsOnInvalidDlcName() {
        DlcDto dto = new DlcDto();
        dto.setDlcName("Bad@Name#");
        dto.setGameTitle("Witcher");
        dto.setPrice(10.0);

        dlcService.save(dto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_throwsOnInvalidGameTitle() {
        DlcDto dto = new DlcDto();
        dto.setDlcName("Expansion");
        dto.setGameTitle("Bad@Title#");
        dto.setPrice(10.0);

        dlcService.save(dto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_throwsOnInvalidId() {
        DlcDto dto = new DlcDto();
        dto.setId("not-a-uuid");
        dto.setDlcName("Expansion");
        dto.setGameTitle("Witcher");
        dto.setPrice(10.0);

        dlcService.save(dto);
    }

    @Test
    public void findByGameTitle_returnsMappedDtos() {
        Dlc dlc1 = new Dlc("id-1");
        Dlc dlc2 = new Dlc("id-2");
        DlcDto dto1 = new DlcDto();
        dto1.setId("id-1");
        DlcDto dto2 = new DlcDto();
        dto2.setId("id-2");

        when(dlcRepository.findByGameTitle("Witcher")).thenReturn(Arrays.asList(dlc1, dlc2));
        when(dlcMapper.toDto(dlc1)).thenReturn(dto1);
        when(dlcMapper.toDto(dlc2)).thenReturn(dto2);

        List<DlcDto> result = dlcService.findByGameTitle("Witcher");

        assertEquals(2, result.size());
        assertEquals("id-1", result.get(0).getId());
        assertEquals("id-2", result.get(1).getId());
    }

    @Test
    public void findByGameTitle_returnsEmpty_whenNoDlcsForGame() {
        when(dlcRepository.findByGameTitle("Unknown")).thenReturn(Collections.emptyList());

        List<DlcDto> result = dlcService.findByGameTitle("Unknown");

        assertTrue(result.isEmpty());
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

        verify(dlcRepository).delete("some-id");
    }
}
