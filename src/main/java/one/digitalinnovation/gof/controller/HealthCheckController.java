package one.digitalinnovation.gof.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Service is up and running");
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> appInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("appName", "lab-padroes-projeto-spring");
        info.put("version", "1.0.0");
        return ResponseEntity.ok(info);
    }
}