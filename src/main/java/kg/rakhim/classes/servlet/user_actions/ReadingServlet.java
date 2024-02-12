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
import kg.rakhim.classes.servlet.authorization.ResponseSender;
import kg.rakhim.classes.utils.ReadingMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@WebServlet("/reading")
public class ReadingServlet extends HttpServlet {
    private final ObjectMapper jsonMapper;
    private final UsersActions userActions;
//    private final ReadingMapper mapper;

    public ReadingServlet() {
        this.jsonMapper = new ObjectMapper();
        this.userActions = new UsersActions();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setEncoding(resp, req);
        ReadingDTO dto = jsonMapper.readValue(req.getInputStream(), ReadingDTO.class);
        MeterReading meterReading = ReadingMapper.convertFromDto(dto);
        ResponseSender.sendResponse(userActions.submitNewReading(meterReading), resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setEncoding(resp, req);
        resp.setContentType("application/json");
        if (req.getParameter("month") == null || req.getParameter("month").isEmpty()){
            sendMessage(req, resp, userActions.viewReadings(req.getParameter("username")));
        }else{
            int month = Integer.parseInt(req.getParameter("month"));
            String username = req.getParameter("username");
            sendMessage(req, resp, userActions.viewReadingsForMonth(username, month));
        }
    }
    private void sendMessage(HttpServletRequest req, HttpServletResponse resp, List<JSONObject> message) throws IOException {
        if (message.isEmpty()){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            jsonMapper.writeValue(resp.getWriter(), "У вас еще нет поданных показаний");
        }else {
            resp.setStatus(HttpServletResponse.SC_OK);
            jsonMapper.writeValue(resp.getWriter(), message.toString());
        }
    }
    private void setEncoding(HttpServletResponse resp, HttpServletRequest req) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
    }
}
