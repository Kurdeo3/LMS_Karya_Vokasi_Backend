package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Administrator;

import  lombok.Data;

@Data
public class AdministratorRequest {
    private String nama;
    private String email;
    private String password;
}
