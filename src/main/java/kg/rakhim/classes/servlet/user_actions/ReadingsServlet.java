package kg.rakhim.classes.servlet.user_actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.rakhim.classes.service.actions.UsersActions;
import kg.rakhim.classes.servlet.ResponseSender;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

@WebServlet("/readings")
public class ReadingsServlet extends HttpServlet {
    private final ObjectMapper jsonMapper;
    private final UsersActions userActions;

    public ReadingsServlet() {
        this.jsonMapper = new ObjectMapper();
        this.userActions = new UsersActions();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        List<JSONObject> message = userActions.viewAllHistory(req.getParameter("username"));
        ResponseSender.sendReadingResponse(resp, message);
    }
}
