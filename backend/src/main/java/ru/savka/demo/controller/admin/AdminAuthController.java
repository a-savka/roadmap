package ru.savka.demo.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savka.demo.dto.AdminLoginRequest;
import ru.savka.demo.dto.AdminLoginResponse;

@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String ADMIN_TOKEN = "admin-token-static";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginRequest request) {
        if (request == null || request.getUsername() == null || request.getPassword() == null) {
            return ResponseEntity.status(401).body(new ErrorResponse("Invalid request"));
        }
        if (ADMIN_USERNAME.equals(request.getUsername()) && ADMIN_PASSWORD.equals(request.getPassword())) {
            AdminLoginResponse response = new AdminLoginResponse(
                    ADMIN_USERNAME,
                    "ADMIN",
                    ADMIN_TOKEN
            );
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body(new ErrorResponse("Invalid admin credentials"));
    }

    private static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}