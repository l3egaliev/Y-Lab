package kg.rakhim.classes.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.dto.RegisterDTO;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.service.RegisterService;
import kg.rakhim.classes.utils.MapperObject;
import kg.rakhim.classes.utils.ResponseSender;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

@WebServlet("/authorize")
public class AuthorizationServlet extends HttpServlet {
    private final ObjectMapper jsonMapper;
    private final RegisterService registerService;


    public AuthorizationServlet(){
        ApplicationContext.loadContext();
        this.registerService = (RegisterService) ApplicationContext.getContext("registerService");
        this.jsonMapper = new ObjectMapper();
        this.jsonMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        register(resp, req);
    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("application/json");
//        resp.setStatus(HttpServletResponse.SC_OK);
//        resp.getWriter().write("");
//    }

    private void login(HttpServletResponse resp, HttpServletRequest req){

    }
    private void register(HttpServletResponse resp, HttpServletRequest req) throws IOException {
        RegisterDTO dto = jsonMapper.readValue(req.getInputStream(), RegisterDTO.class);
        boolean status = ResponseSender.validation(dto, resp, jsonMapper);
        if (status){
            User user = (User) MapperObject.map(dto, User.class);
            Map<Boolean, JSONObject> result = registerService.registerUser(user);
            ResponseSender.sendRegisterMessage(result,resp);
        }
    }
}
