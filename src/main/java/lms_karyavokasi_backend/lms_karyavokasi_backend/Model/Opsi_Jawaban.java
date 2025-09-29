package lms_karyavokasi_backend.lms_karyavokasi_backend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="opsi_jawaban")
public class Opsi_Jawaban {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String opsi; //A - B - C - D
    private String teksJawaban;
    private Integer skor;

    @ManyToOne
    @JoinColumn(name="soal_id")
    private Soal_Assessment soal;
}
