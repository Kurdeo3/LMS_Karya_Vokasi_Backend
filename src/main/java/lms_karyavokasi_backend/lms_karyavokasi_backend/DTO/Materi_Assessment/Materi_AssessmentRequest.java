package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Assessment;

import java.util.List;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Soal_Assessment.Soal_AssessmentRequest;

import lombok.Data;

@Data
public class Materi_AssessmentRequest {
    private String judul;
    private String deskripsi;
    private String tipe; //Tipe Assessment = Tes / Ujian
    private Long topikId;
    private List<Soal_AssessmentRequest> soalList;
}
