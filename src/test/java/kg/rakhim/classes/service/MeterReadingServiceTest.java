package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.MeterReadingDAO;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.MeterType;
import kg.rakhim.classes.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.auditable.data.UserData;
import ru.auditable.data.UserInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MeterReadingServiceTest {

    @MockBean
    private MeterReadingDAO meterReadingDAO;

    @MockBean
    private UserData userData;

    @Autowired
    private MeterReadingService meterReadingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Testing method findById()")
    @Test
    void testFindById() {
        // Arrange
        MeterReading expectedMeterReading = new MeterReading();
        when(meterReadingDAO.get(1)).thenReturn(Optional.of(expectedMeterReading));

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
        when(meterReadingDAO.getAll()).thenReturn(List.of(meterReading1, meterReading2));

        // Act
        List<MeterReading> result = meterReadingService.findAll();

        // Assert
        assertThat(result).hasSize(2);
    }

    @DisplayName("Testing method saveReading()")
    @Test
    void testSave() {
        MeterReading meterReadingToSave = new MeterReading(new User("user"),
                1311, new MeterType("отопление"), LocalDateTime.now());
        MeterReading meterReading = new MeterReading(new User("user"),
                2324, new MeterType("горячая вода"), LocalDateTime.now());
        MeterReading meterReading2 = new MeterReading(new User("user"),
                5353, new MeterType("холодная вода"), LocalDateTime.now());

        when(userData.getCurrentUser()).thenReturn(new UserInfo("user"));
        when(meterReadingDAO.getByUser("user")).thenReturn(List.of(meterReading, meterReading2));
        meterReadingService.saveReading(meterReadingToSave);

        verify(meterReadingDAO, times(1)).save(meterReadingToSave);
    }
}
