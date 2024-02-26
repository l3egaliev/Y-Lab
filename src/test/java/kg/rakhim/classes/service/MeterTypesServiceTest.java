package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.MeterTypesDAO;
import kg.rakhim.classes.models.MeterType;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.auditable.data.UserData;
import ru.auditable.data.UserInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MeterTypesServiceTest {
    @Autowired
    private MeterTypesService meterTypesService;

    @MockBean
    private MeterTypesDAO meterTypesDAO;

    @MockBean
    private UserService userService;

    @MockBean
    private UserData userData;

    @BeforeEach
    void setUp() {
        when(userData.getCurrentUser()).thenReturn(new UserInfo("admin"));
    }

    @Test
    @DisplayName("Testing method saveType() with non-existing type")
    void testSaveType_NonExistingType() {
        String newType = "newType";

        when(meterTypesDAO.isExists(newType)).thenReturn(false);
        when(userService.isAdmin("admin")).thenReturn(true);

        boolean result = meterTypesService.saveType(newType, new ArrayList<>());
        assertThat(result).isTrue();

        verify(meterTypesDAO, times(1)).isExists(newType);
    }

    @Test
    @DisplayName("Testing method saveType() with existing type")
    void testSaveType_ExistingType() {
        String existingType = "existingType";

        when(meterTypesDAO.isExists(existingType)).thenReturn(true);

        boolean result = meterTypesService.saveType(existingType, new ArrayList<>());
        assertThat(result).isFalse();
        assertThat(meterTypesService.isExistsType(existingType)).isTrue();
    }
}
