package kg.rakhim.classes.servlet.user_actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.rakhim.classes.dto.ReadingDTO;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.service.actions.UsersActions;
import kg.rakhim.classes.servlet.ResponseSender;
import kg.rakhim.classes.utils.ReadingMapper;

import java.io.IOException;

@WebServlet("/reading/add")
public class SendReadingServlet extends HttpServlet {
    private final ObjectMapper jsonMapper;
    private final UsersActions userActions;

    public SendReadingServlet() {
        this.jsonMapper = new ObjectMapper();
        this.userActions = new UsersActions();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        ReadingDTO dto = jsonMapper.readValue(req.getInputStream(), ReadingDTO.class);
        MeterReading meterReading = ReadingMapper.convertFromDto(dto);
        ResponseSender.sendResponse(userActions.submitNewReading(meterReading), resp);
    }

}
