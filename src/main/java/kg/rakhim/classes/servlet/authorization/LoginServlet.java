package kg.rakhim.classes.servlet.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.rakhim.classes.annotations.Auditable;
import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.dto.AuthorizeDTO;
import kg.rakhim.classes.service.RegisterService;
import kg.rakhim.classes.servlet.ResponseSender;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final ObjectMapper jsonMapper;
    private final RegisterService registerService;
    public LoginServlet() {
        this.registerService = (RegisterService) ApplicationContext.getContext("registerService");
        this.jsonMapper = new ObjectMapper();
    }

    @Override
    @Auditable
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthorizeDTO dto = jsonMapper.readValue(req.getInputStream(), AuthorizeDTO.class);
        boolean status = ResponseSender.sendValidationResp(dto, resp, jsonMapper);
        if (status){
            Map<Boolean, JSONObject> result = registerService.loginUser(dto.getUsername(), dto.getPassword());
            ResponseSender.sendResponse(result,resp);
        }
    }
}
