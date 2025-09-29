package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Assessment;

import java.util.List;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Soal_Assessment.Soal_AssessmentResponse;
import lombok.Data;

@Data
public class Materi_AssessmentResponse {
    private Long id;
    private String judul;
    private String deskripsi;
    private String tipe; //Tipe Assessment = Tes / Ujian
    private Long topikId;
    private String namaTopik;
    private List<Soal_AssessmentResponse> soalList;
}
