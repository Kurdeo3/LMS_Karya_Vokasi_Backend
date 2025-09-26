package lms_karyavokasi_backend.lms_karyavokasi_backend.Service;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Login.LoginRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Login.LoginResponse;

public interface AuthService {
    LoginResponse loginAdministrator(LoginRequest request);
    LoginResponse loginPengajar(LoginRequest request);
    void logout(String token);
}
