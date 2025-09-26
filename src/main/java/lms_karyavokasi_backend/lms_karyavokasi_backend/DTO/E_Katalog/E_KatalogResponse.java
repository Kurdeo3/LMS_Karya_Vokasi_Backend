package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.E_Katalog;

import java.util.Date;

import lombok.Data;

@Data
public class E_KatalogResponse {
    private Long id;
    private String judul_e_katalog;
    private String deskripsi;
    private String thumbnail;
    private String penjelasan_paket;
    private Date start_date;
    private Integer durasi_bulan;
    private String status;

    // Data Kategori
    private Long kategoriId;
    private String kategoriNama;
    
    // Data Pengajar
    private Long pengajarId;
    private String pengajarEmail;
    private String pengajarNama;
}
