package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.interfaces.MeterTypesDAOIn;
import kg.rakhim.classes.models.MeterType;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class MeterTypesServiceTest {

    private MeterTypesService meterTypesService;
    private FakeMeterTypesDAO fakeMeterTypesDAO;

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("ylab")
            .withUsername("postgres")
            .withPassword("postgres");

    @BeforeEach
    void setUp() {
        String jdbcUrl = postgresContainer.getJdbcUrl();
        String username = postgresContainer.getUsername();
        String password = postgresContainer.getPassword();

        fakeMeterTypesDAO = new FakeMeterTypesDAO(); // Фиктивная реализация DAO
        meterTypesService = new MeterTypesService(fakeMeterTypesDAO);
    }

    @Test
    @DisplayName("Testing method saveType() with non-existing type")
    void testSaveType_NonExistingType() {
        String newType = "newType";
        assertThat(fakeMeterTypesDAO.isExists(newType)).isFalse();
        int result = meterTypesService.saveType(newType);
        assertThat(result).isEqualTo(1);
        assertThat(fakeMeterTypesDAO.getSavedTypes()).contains(new MeterType(newType));
    }

    @Test
    @DisplayName("Testing method saveType() with existing type")
    void testSaveType_ExistingType() {
        // Arrange
        String existingType = "existingType";
        fakeMeterTypesDAO.save(new MeterType(existingType)); // Добавляем существующий тип
        int result = meterTypesService.saveType(existingType);
        assertThat(result).isEqualTo(0);
        assertThat(fakeMeterTypesDAO.isExists(existingType)).isTrue();
        assertThat(fakeMeterTypesDAO.getSavedTypes()).containsExactly(new MeterType(existingType));
    }

    // Фиктивная реализация MeterTypesDAO
    private static class FakeMeterTypesDAO implements MeterTypesDAOIn {
        private final List<MeterType> savedTypes = new ArrayList<>();

        public boolean isExists(String type) {
            return savedTypes.stream().anyMatch(t -> t.getType().equals(type));
        }

        @Override
        public MeterType get(int id) {
            return savedTypes.get(id);
        }

        @Override
        public List<MeterType> getAll() {
            return savedTypes;
        }

        @Override
        public void save(MeterType type) {
            savedTypes.add(type);
        }

        public List<MeterType> getSavedTypes() {
            return savedTypes;
        }
    }
}
