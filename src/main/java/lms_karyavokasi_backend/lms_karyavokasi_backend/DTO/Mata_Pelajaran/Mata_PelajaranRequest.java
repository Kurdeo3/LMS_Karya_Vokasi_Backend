package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Mata_Pelajaran;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Mata_PelajaranRequest {
    private String namaMataPelajaran;
    private String thumbnail;
    private String deskripsi;
    private Long kategoriId; 

    @JsonProperty("eKatalogId")
    private Long eKatalogId;
}
