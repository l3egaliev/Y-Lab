package kg.rakhim.classes.controller;

import kg.rakhim.classes.dto.TypeDTO;
import kg.rakhim.classes.service.AdminService;
import kg.rakhim.classes.service.MeterTypesService;
import kg.rakhim.classes.utils.ErrorSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdminControllerTest {

    @MockBean
    private MeterTypesService meterTypesService;

    @MockBean
    private AdminService adminService;

    @Mock
    private TypeDTO typeDTO;

    @Mock
    private BindingResult bindingResult;

    @Autowired
    private AdminController adminController;

    @Test
    @DisplayName("Test addType method")
    void testAddType() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(meterTypesService.saveType(typeDTO.getType(), Collections.emptyList())).thenReturn(true);

        ResponseEntity<Map<String, Object>> response = adminController.addType(typeDTO, bindingResult);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsEntry("message", "Новый тип добавлен");
    }

    @Test
    @DisplayName("Test readings method without parameters")
    void testReadings_NoParameters() {
        ResponseEntity<Map<String, Object>> response = adminController.readings(null, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).containsEntry("response", Collections.emptyList());
    }

    @Test
    @DisplayName("Test readings method with month parameter")
    void testReadings_WithMonthParameter() {
        when(adminService.readingsOfAllUsersForMonth(1)).thenReturn(Collections.emptyList());

        ResponseEntity<Map<String, Object>> response = adminController.readings(1, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).containsEntry("response", Collections.emptyList());
    }

    @Test
    @DisplayName("Test readings method with username parameter")
    void testReadings_WithUsernameParameter() {
        when(adminService.historyOfUserReadings("testUser")).thenReturn(Collections.emptyList());

        ResponseEntity<Map<String, Object>> response = adminController.readings(null, "testUser");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).containsEntry("response", Collections.emptyList());
    }

    @Test
    @DisplayName("Test readings method with month and username parameters")
    void testReadings_WithMonthAndUsernameParameters() {
        when(adminService.readingsOfOneUserForMonth("testUser", 1)).thenReturn(Collections.emptyList());

        ResponseEntity<Map<String, Object>> response = adminController.readings(1, "testUser");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).containsEntry("response", Collections.emptyList());
    }
}
