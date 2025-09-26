package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Pelajar;

import  lombok.Data;

@Data
public class PelajarRequest {
    private String nama;
    private String email;
    private String telfon;
    private String alamat;
    private String jenisKelamin;
    private String jenjangPendidikan;
    private String foto;
    private String status; 
    private String password;
}
