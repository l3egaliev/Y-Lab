//package kg.rakhim.classes.service;
//
//import kg.rakhim.classes.context.UserContext;
//import kg.rakhim.classes.context.UserDetails;
//import kg.rakhim.classes.dao.MeterTypesDAO;
//import kg.rakhim.classes.dao.interfaces.MeterTypesDAOIn;
//import kg.rakhim.classes.models.MeterType;
//import org.junit.jupiter.api.*;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//@Testcontainers
//class MeterTypesServiceTest {
//
//    private MeterTypesService meterTypesService;
//    @Mock
//    private MeterTypesDAO meterTypesDAO;
//    @Mock
//    private UserService userService;
//    @Mock
//    private UserContext userContext;
//
//    @Container
//    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
//            .withDatabaseName("ylab")
//            .withUsername("postgres")
//            .withPassword("postgres");
//
//    @BeforeEach
//    void setUp() {
//        String jdbcUrl = postgresContainer.getJdbcUrl();
//        String username = postgresContainer.getUsername();
//        String password = postgresContainer.getPassword();
//
//        MockitoAnnotations.openMocks(this);
//        meterTypesService = new MeterTypesService(meterTypesDAO, userService, userContext);
//    }
//
//    @Test
//    @DisplayName("Testing method saveType() with non-existing type")
//    void testSaveType_NonExistingType() {
//        String newType = "newType";
//        UserDetails userDetails = new UserDetails("admin");
//
//        when(userContext.getCurrentUser().getUsername()).thenReturn(userDetails.getUsername());
//        when(userService.isAdmin(userDetails.getUsername())).thenReturn(true);
//        when(meterTypesDAO.isExists(newType)).thenReturn(false);
//        when(meterTypesDAO.getAll()).thenReturn(List.of(new MeterType(newType)));
//
//        assertThat(meterTypesDAO.isExists(newType)).isFalse();
//        boolean result = meterTypesService.saveType(newType, new ArrayList<>());
//        assertThat(result).isTrue();
//        assertThat(meterTypesDAO.getAll()).contains(new MeterType(newType));
//
//        verify(meterTypesDAO, times(1)).save(new MeterType(newType));
//        verify(meterTypesDAO, times(1)).isExists(newType);
//        verify(meterTypesDAO, times(1)).getAll();
//        verify(userService, times(1)).isAdmin("admin");
//    }
//
//    @Test
//    @DisplayName("Testing method saveType() with existing type")
//    void testSaveType_ExistingType() {
//        // Arrange
//        String existingType = "existingType";
//        meterTypesDAO.save(new MeterType(existingType)); // Добавляем существующий тип
//        boolean result = meterTypesService.saveType(existingType, new ArrayList<>());
//        assertThat(result).isFalse();
//        assertThat(meterTypesDAO.isExists(existingType)).isTrue();
//        assertThat(meterTypesDAO.getAll()).containsExactly(new MeterType(existingType));
//    }
//}
