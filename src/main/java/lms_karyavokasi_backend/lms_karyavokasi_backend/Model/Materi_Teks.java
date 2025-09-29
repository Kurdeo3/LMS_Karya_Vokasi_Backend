package lms_karyavokasi_backend.lms_karyavokasi_backend.Model;

import jakarta.persistence.Column;
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
@Table(name="materi_teks")
public class Materi_Teks {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String judul;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String deskripsi;

    @Column(nullable=false)
    private String urlTeks;

    @ManyToOne
    @JoinColumn(name = "topik_id", nullable=false)
    private Topik_Mata_Pelajaran topik;
}
