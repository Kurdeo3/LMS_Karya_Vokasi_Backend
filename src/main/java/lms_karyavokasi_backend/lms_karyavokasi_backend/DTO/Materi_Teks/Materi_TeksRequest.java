package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Teks;

import lombok.Data;

@Data
public class Materi_TeksRequest {
    private String judul;
    private String deskripsi;
    private String urlTeks;
    private Long topikId;
}
