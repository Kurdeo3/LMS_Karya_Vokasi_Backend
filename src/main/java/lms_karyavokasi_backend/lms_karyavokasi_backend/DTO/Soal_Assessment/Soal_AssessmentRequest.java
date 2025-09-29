package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Soal_Assessment;

import java.util.List;

import lombok.Data;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Opsi_Jawaban.Opsi_JawabanRequest;


@Data
public class Soal_AssessmentRequest {
    private Long id;
    private String pertanyaan;
    private String tipeSoal;
    private String kunciJawabanEssay;
    private List<Opsi_JawabanRequest> opsiJawabanList;
}
