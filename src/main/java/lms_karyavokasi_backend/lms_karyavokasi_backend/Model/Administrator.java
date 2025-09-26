package lms_karyavokasi_backend.lms_karyavokasi_backend.Model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Token.AdministratorToken;
import lombok.Data;

@Entity
@Data
@Table(name="administrator")
public class Administrator {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String nama;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // Relasi OneToMany ke AdministratorToken
    @OneToMany(mappedBy = "administrator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdministratorToken> tokens;

}
