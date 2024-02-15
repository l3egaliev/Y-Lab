package kg.rakhim.classes.servlet.user_actions;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.rakhim.classes.service.actions.UsersActions;
import kg.rakhim.classes.servlet.ResponseSender;

import java.io.IOException;

@WebServlet("/actualReadings")
public class ActualReadingsServlet extends HttpServlet {
    private final UsersActions userActions;

    public ActualReadingsServlet() {
        this.userActions = new UsersActions();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        ResponseSender.sendReadingResponse(resp, userActions.viewActualReadings(req.getParameter("username")));
    }
}
