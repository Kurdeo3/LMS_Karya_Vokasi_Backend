package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Pengajar;

import  lombok.Data;

@Data
public class PengajarRequest {
    private String nama;
    private String email;
    private String telfon;
    private String alamat;
    private String deskripsi;
    private String jenisKelamin;
    private String jenjangPendidikan;
    private String status; 
    private String password;
}
