package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.E_Katalog;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class E_KatalogRequest {
    private String judul_e_katalog;
    private String deskripsi;
    private String thumbnail;
    private String penjelasan_paket;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date start_date;
    private Integer durasi_bulan;
    private Long kategoriId;
    private String status;
}
