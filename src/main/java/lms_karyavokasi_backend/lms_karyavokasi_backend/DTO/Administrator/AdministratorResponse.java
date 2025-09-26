package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Administrator;

import  lombok.Data;

@Data
public class AdministratorResponse {
    private Long id;
    private String nama;
    private String email;
    private String password;
}
