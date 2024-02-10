package kg.rakhim.classes.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import kg.rakhim.classes.dao.MeterReadingDAO;
import kg.rakhim.classes.dao.MeterTypesDAO;
import kg.rakhim.classes.dao.UserDAO;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.MeterType;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.repository.impl.MeterReadingRepositoryImpl;
import kg.rakhim.classes.repository.impl.MeterTypeRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MeterReadingServiceTest {
    private MeterReadingDAO meterReadingDAO;
    @Mock
    private MeterReadingRepositoryImpl meterReadingRepository;
    @Mock
    private MeterTypesService meterTypesService;
    private MeterReadingService meterReadingService;

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("ylab")
            .withUsername("postgres")
            .withPassword("postgres");
    @BeforeEach
    void setUp() {
        meterReadingDAO = new MeterReadingDAO();
        meterReadingDAO.setUsername(postgresContainer.getUsername());
        meterReadingDAO.setJdbcUrl(postgresContainer.getJdbcUrl());
        meterReadingDAO.setPassword(postgresContainer.getPassword());

        MockitoAnnotations.openMocks(this);
        meterReadingService = new MeterReadingService(meterReadingRepository, meterTypesService);
    }

    @DisplayName("Testing method findById()")
    @Test
    void testFindById() {
        MeterReading expectedMeterReading = new MeterReading();
        Optional<MeterReading> result = meterReadingService.findById(1);
        assertTrue(result.isPresent());
        assertEquals(expectedMeterReading, result.get());
    }

    @DisplayName("Testing method findAll()")
    @Test
    void testFindAll() {
        meterReadingService.save(new MeterReading(new User("admin"), 1313, new MeterType("холодная вода")));
        meterReadingService.save(new MeterReading(new User("admin"), 1313, new MeterType("горячая вода")));
        List<MeterReading> result = meterReadingService.findAll();
        assertThat(result).hasSize(2);
    }

    @DisplayName("Testing method save()")
    @Test
    void testSave() {
        MeterReading meterReadingToSave = new MeterReading(new User("admin"), 1311, new MeterType("отопление"));
        meterReadingService.save(meterReadingToSave);
    }


}

