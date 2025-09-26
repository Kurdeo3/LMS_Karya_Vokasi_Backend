package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Mata_Pelajaran;

import lombok.Data;

@Data
public class Mata_PelajaranResponse {
    private Long id;
    private String namaMataPelajaran;
    private String thumbnail;
    private String deskripsi;

    private Long kategoriId;
    private String kategoriNama;

    private Long eKatalogId;
    private String eKatalogJudul;
}
