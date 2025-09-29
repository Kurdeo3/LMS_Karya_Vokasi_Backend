package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Teks;

import lombok.Data;

@Data
public class Materi_TeksResponse {
    private Long id;
    private String judul;
    private String deskripsi;
    private String urlTeks;
    private Long topikId;
    private String namaTopik;
}
