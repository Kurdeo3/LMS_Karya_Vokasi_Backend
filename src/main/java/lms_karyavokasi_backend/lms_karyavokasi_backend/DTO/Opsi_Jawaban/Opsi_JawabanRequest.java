package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Opsi_Jawaban;

import lombok.Data;

@Data
public class Opsi_JawabanRequest {
    private Long id;
    private String opsi;
    private String teksJawaban;
    private Integer skor;
}
