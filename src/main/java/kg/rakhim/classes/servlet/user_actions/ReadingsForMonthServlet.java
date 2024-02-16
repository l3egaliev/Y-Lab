//package kg.rakhim.classes.servlet.user_actions;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import kg.rakhim.classes.service.actions.UsersActions;
//import kg.rakhim.classes.servlet.ResponseSender;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//
//@WebServlet("/readingsForMonth")
//public class ReadingsForMonthServlet extends HttpServlet {
//    private final ObjectMapper jsonMapper;
//    private final UsersActions userActions;
//
//    public ReadingsForMonthServlet() {
//        this.jsonMapper = new ObjectMapper();
//        this.userActions = new UsersActions();
//    }
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        setEncoding(resp, req);
//        resp.setContentType("application/json");
//        int month = Integer.parseInt(req.getParameter("month"));
//        String username = req.getParameter("username");
//        ResponseSender.sendReadingResponse(resp, userActions.viewReadingsForMonth(username, month));
//    }
//    private void setEncoding(HttpServletResponse resp, HttpServletRequest req) throws UnsupportedEncodingException {
//        req.setCharacterEncoding("UTF-8");
//        resp.setCharacterEncoding("UTF-8");
//    }
//}
