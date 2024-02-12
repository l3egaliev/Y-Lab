package kg.rakhim.classes.servlet.admin_actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.rakhim.classes.dto.AddReadingDTO;
import kg.rakhim.classes.service.actions.AdminActions;
import kg.rakhim.classes.servlet.ResponseSender;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/reading")
public class ReadingServlet extends HttpServlet {
    private final ObjectMapper jsonMapper;
    private final AdminActions actions;

    public ReadingServlet() {
        jsonMapper = new ObjectMapper();
        actions = new AdminActions();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        AddReadingDTO dto = jsonMapper.readValue(req.getInputStream(), AddReadingDTO.class);
        boolean isValid = ResponseSender.sendValidationResp(dto, resp, jsonMapper);
        if (isValid){
            Map<Boolean, JSONObject> message = actions.addNewType(dto.getAdmin(), dto.getType());
            ResponseSender.sendResponse(message, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if (req.getParameter("username") != null){
            List<JSONObject> result = actions.readingHistoryOfUser(req.getParameter("username"));
            ResponseSender.sendReadingResponse(resp, result);
        }else if(req.getParameter("month") != null){
            List<JSONObject> forMonth = actions.readingsOfAllUsersForMonth(Integer.parseInt(req.getParameter("month")));
            ResponseSender.sendReadingResponse(resp, forMonth);
        }else {
            List<JSONObject> allReadings = actions.actualReadingsOfAllUsers();
            ResponseSender.sendReadingResponse(resp, allReadings);
        }
    }
}
