package lms_karyavokasi_backend.lms_karyavokasi_backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "pelajar")
public class Pelajar {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String nama;
    private String telfon;
    private String jenisKelamin;
    private String alamat;
    private String jenjangPendidikan;
    private String foto;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable=false)
    private String status;

}