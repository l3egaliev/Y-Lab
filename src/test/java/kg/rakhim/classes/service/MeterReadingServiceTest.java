package kg.rakhim.classes.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import kg.rakhim.classes.dao.MeterReadingDAO;
import kg.rakhim.classes.models.MeterReading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.*;

class MeterReadingServiceTest {

    @Mock
    private MeterReadingDAO mockMeterReadingDAO;
    @Mock
    private MeterTypesService mockMeterTypesService;

    private MeterReadingService meterReadingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        meterReadingService = new MeterReadingService(mockMeterReadingDAO, mockMeterTypesService);
    }

    @DisplayName("Testing method findById()")
    @Test
    void testFindById() {
        MeterReading expectedMeterReading = new MeterReading();
        when(mockMeterReadingDAO.get(1)).thenReturn(expectedMeterReading);
        Optional<MeterReading> result = meterReadingService.findById(1);
        assertTrue(result.isPresent());
        assertEquals(expectedMeterReading, result.get());
    }

    @DisplayName("Testing method findAll()")
    @Test
    void testFindAll() {
        List<MeterReading> expectedMeterReadings = Arrays.asList(
                new MeterReading(),
                new MeterReading()
        );
        when(mockMeterReadingDAO.getAll()).thenReturn(expectedMeterReadings);
        List<MeterReading> result = meterReadingService.findAll();
        assertEquals(expectedMeterReadings, result);
    }

    @DisplayName("Testing method save()")
    @Test
    void testSave() {
        MeterReading meterReadingToSave = new MeterReading();
        meterReadingService.save(meterReadingToSave);
        verify(mockMeterReadingDAO).save(meterReadingToSave);
    }


}

