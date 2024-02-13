package kg.rakhim.classes.service;

import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.MeterType;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.repository.impl.MeterReadingRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MeterReadingServiceTest {

    @Mock
    private MeterReadingRepositoryImpl meterReadingRepository;

    private MeterReadingService meterReadingService;

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("ylab")
            .withUsername("postgres")
            .withPassword("postgres");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        meterReadingService = new MeterReadingService(meterReadingRepository);
    }

    @DisplayName("Testing method findById()")
    @Test
    void testFindById() {
        // Arrange
        MeterReading expectedMeterReading = new MeterReading();
        when(meterReadingRepository.findById(1)).thenReturn(Optional.of(expectedMeterReading));

        // Act
        Optional<MeterReading> result = meterReadingService.findById(1);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedMeterReading);
    }

    @DisplayName("Testing method findAll()")
    @Test
    void testFindAll() {
        // Arrange
        MeterReading meterReading1 = new MeterReading(new User("admin"), 1313, new MeterType("холодная вода"));
        MeterReading meterReading2 = new MeterReading(new User("admin"), 1313, new MeterType("горячая вода"));
        when(meterReadingRepository.findAll()).thenReturn(List.of(meterReading1, meterReading2));

        // Act
        List<MeterReading> result = meterReadingService.findAll();

        // Assert
        assertThat(result).hasSize(2);
    }

    @DisplayName("Testing method save()")
    @Test
    void testSave() {
        // Arrange
        MeterReading meterReadingToSave = new MeterReading(new User("admin"), 1311, new MeterType("отопление"));

        // Act
        meterReadingService.save(meterReadingToSave);

        // Assert
        verify(meterReadingRepository, times(1)).save(meterReadingToSave);
    }
}


