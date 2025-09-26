package lms_karyavokasi_backend.lms_karyavokasi_backend.Model;

import java.util.ArrayList;
import java.util.Date;
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
@Table(name = "e_katalog")
public class E_Katalog {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String judul_e_katalog;

    @Column(nullable = false)
    private String deskripsi;

    @Column(nullable = false)
    private String thumbnail;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String penjelasan_paket;

    @Column(nullable = false)
    private Date start_date;

    @Column(nullable = false)
    private Integer durasi_bulan;

    //RELASI BELONGS TO E_KATEGORI
    @ManyToOne
    @JoinColumn(name = "kategori_e_katalog_id", nullable = true)
    private Kategori_E_Katalog kategoriEKatalog;

    @OneToMany(mappedBy = "eKatalog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mata_Pelajaran> mataPelajaranList = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "pengajar_id", nullable = false)
    private Pengajar pengajar;

    private String status; // DRAFT / PUBLISH
}
