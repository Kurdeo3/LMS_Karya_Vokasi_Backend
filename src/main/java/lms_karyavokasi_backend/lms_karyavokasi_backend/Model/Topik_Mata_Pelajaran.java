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
@Table(name="topik_mata_pelajaran")
public class Topik_Mata_Pelajaran {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nama_topik;

    //RELASI KE TABEL Mata Pelajaran
    @ManyToOne
    @JoinColumn(name = "mata_pelajaran_id", nullable = false)
    private Mata_Pelajaran mataPelajaran;
}
