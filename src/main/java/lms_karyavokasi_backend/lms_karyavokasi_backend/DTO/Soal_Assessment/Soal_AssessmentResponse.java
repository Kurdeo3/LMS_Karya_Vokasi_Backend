package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Soal_Assessment;

import java.util.List;

import lombok.Data;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Opsi_Jawaban.Opsi_JawabanResponse;

@Data

public class Soal_AssessmentResponse {
    private Long id;
    private String pertanyaan;
    private String tipeSoal;
    private String kunciJawabanEssay;
    private List<Opsi_JawabanResponse> opsiJawabanList;
}
