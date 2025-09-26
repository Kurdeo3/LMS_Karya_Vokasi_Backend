package lms_karyavokasi_backend.lms_karyavokasi_backend.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Token.PengajarToken;
import lombok.Data;

@Entity
@Data
@Table(name = "pengajar")
public class Pengajar {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String nama;
    private String telfon;
    private String jenisKelamin;
    private String alamat;
    private String jenjangPendidikan;

    @Column(nullable = true)
    private String foto;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "TEXT")
    private String deskripsi;

    @Column(nullable=false)
    private String status;

    @OneToMany(mappedBy = "pengajar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PengajarToken> tokens;

    @OneToMany(mappedBy = "pengajar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<E_Katalog> eKatalogList = new ArrayList<>();
}
