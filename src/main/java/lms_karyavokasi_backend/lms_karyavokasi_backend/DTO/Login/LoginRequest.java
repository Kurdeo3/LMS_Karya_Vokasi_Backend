package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Login;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
