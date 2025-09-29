package lms_karyavokasi_backend.lms_karyavokasi_backend.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="soal_assessment")
public class Soal_Assessment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String pertanyaan;

    @Column(nullable=false)
    private String tipeSoal; //Pilihan Ganda atau Essay

    @Column(nullable=true)
    private String kunciJawabanEssay; //Diisi untuk Tipe soal Essay saja

    @ManyToOne
    @JoinColumn(name = "materi_assessment_id")
    private Materi_Assessment materiAssessment;

    @OneToMany(mappedBy= "soal", cascade= CascadeType.ALL, orphanRemoval=true)
    private List<Opsi_Jawaban> opsiJawabanList = new ArrayList<>();
}
