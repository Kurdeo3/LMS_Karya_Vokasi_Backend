package lms_karyavokasi_backend.lms_karyavokasi_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Login.LoginRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Login.LoginResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login/administrator")
    public LoginResponse loginAdmin(@RequestBody LoginRequest request) {
        return authService.loginAdministrator(request);
    }

    @PostMapping("/login/pengajar")
    public LoginResponse loginPengajar(@RequestBody LoginRequest request) {
        return authService.loginPengajar(request);
    }

    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String token) {
        authService.logout(token.replace("Bearer ", ""));
        return "Logout berhasil";
    }
}
