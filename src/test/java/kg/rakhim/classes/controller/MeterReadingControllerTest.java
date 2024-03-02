package kg.rakhim.classes.controller;

import kg.rakhim.classes.dto.ReadingResponseDTO;
import kg.rakhim.classes.service.MeterReadingService;
import kg.rakhim.classes.utils.ReadingValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class MeterReadingControllerTest {

    @MockBean
    private MeterReadingService meterReadingService;

    @Mock
    private ReadingValidator readingValidator;

    @InjectMocks
    private MeterReadingController meterReadingController;

    @BeforeEach
    void setUp() {
        meterReadingController = new MeterReadingController(meterReadingService, readingValidator);
    }

    @Test
    @DisplayName("Test actualReadings method with non-empty readings")
    void testActualReadings_NonEmptyReadings() {
        List<ReadingResponseDTO> readings = new ArrayList<>();
        readings.add(ReadingResponseDTO.builder().build());
        when(meterReadingService.findActualReadings()).thenReturn(readings);

        ResponseEntity<List<ReadingResponseDTO>> response = meterReadingController.actualReadings();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(readings);
    }

    @Test
    @DisplayName("Test actualReadings method with empty readings")
    void testActualReadings_EmptyReadings() {
        when(meterReadingService.findActualReadings()).thenReturn(Collections.emptyList());

        ResponseEntity<List<ReadingResponseDTO>> response = meterReadingController.actualReadings();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }
}
