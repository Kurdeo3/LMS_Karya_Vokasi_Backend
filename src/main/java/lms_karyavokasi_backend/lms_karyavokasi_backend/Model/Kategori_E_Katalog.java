package lms_karyavokasi_backend.lms_karyavokasi_backend.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="kategori_e_katalog")
public class Kategori_E_Katalog {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nama_kategori;

    //RELASI KE TABEL E-KATALOG (ONE TO MANY)
    @OneToMany(mappedBy= "kategoriEKatalog")
    private List<E_Katalog> eKatalogList = new ArrayList<>();
}
