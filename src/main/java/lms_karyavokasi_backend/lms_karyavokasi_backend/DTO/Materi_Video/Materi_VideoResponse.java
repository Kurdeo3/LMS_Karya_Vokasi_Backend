package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Video;

import lombok.Data;

@Data
public class Materi_VideoResponse {
    private Long id;
    private String judul;
    private String deskripsi;
    private String urlVideo;
    private Long topikId;
    private String namaTopik;
}
