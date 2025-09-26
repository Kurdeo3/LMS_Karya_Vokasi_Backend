package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Login;

import lombok.Data;

@Data
public class LoginResponse {
    private Long userId;
    private String nama;
    private String email;
    private String token;
    private String role;
}
