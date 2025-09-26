package lms_karyavokasi_backend.lms_karyavokasi_backend.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "mata_pelajaran")
public class Mata_Pelajaran {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false ,name = "nama_mata_pelajaran")
    private String namaMataPelajaran;

    @Column(nullable = false)
    private String thumbnail;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String deskripsi;
    
    //RELASI BELONGS TO Kategori Mata Pelajaran
    @ManyToOne
    @JoinColumn(name = "kategori_mata_pelajaran_id", nullable = true)
    private Kategori_Mata_Pelajaran kategoriMataPelajaran;

    //1 Mata Pelajaran Memiliki Relasi ke 1 atau N Topik
    @OneToMany(mappedBy = "mataPelajaran", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topik_Mata_Pelajaran> topikList = new ArrayList<>();
    
    //RELASI MANY TO ONE KE E_KATALOG
    @ManyToOne
    @JoinColumn(name = "e_katalog_id", nullable=false)
    private E_Katalog eKatalog;
}
