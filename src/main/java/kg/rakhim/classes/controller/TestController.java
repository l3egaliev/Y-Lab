package kg.rakhim.classes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<String> getTest(){
        String suc = "Test is done";
        return ResponseEntity.ok(suc);
    }
}
