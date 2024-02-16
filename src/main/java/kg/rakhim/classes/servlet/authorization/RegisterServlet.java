//package kg.rakhim.classes.servlet.authorization;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import kg.rakhim.classes.dto.AuthorizeDTO;
//import kg.rakhim.classes.models.User;
//import kg.rakhim.classes.service.RegisterService;
//import kg.rakhim.classes.servlet.ResponseSender;
//import kg.rakhim.classes.utils.MapperObject;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.Map;
//
//@WebServlet("/register")
//public class RegisterServlet extends HttpServlet {
//    private final ObjectMapper jsonMapper;
//    private final RegisterService registerService;
//
//
//    public RegisterServlet(){
//        this.registerService = (RegisterService) ApplicationContext.getContext("registerService");
//        this.jsonMapper = new ObjectMapper();
//        this.jsonMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("UTF-8");
//        resp.setCharacterEncoding("UTF-8");
//        AuthorizeDTO dto = jsonMapper.readValue(req.getInputStream(), AuthorizeDTO.class);
//        boolean status = ResponseSender.sendValidationResp(dto, resp, jsonMapper);
//        if (status){
//            User user = (User) MapperObject.map(dto, User.class);
//            Map<Boolean, JSONObject> result = registerService.registerUser(user);
//            ResponseSender.sendResponse(result,resp);
//        }
//    }
//}
