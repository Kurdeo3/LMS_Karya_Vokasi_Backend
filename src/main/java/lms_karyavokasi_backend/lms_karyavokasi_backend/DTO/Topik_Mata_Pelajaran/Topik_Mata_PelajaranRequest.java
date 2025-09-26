package lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Topik_Mata_Pelajaran;

import  lombok.Data;

@Data
public class Topik_Mata_PelajaranRequest {
    private String nama_topik;
    private Long mataPelajaranId; 
}
