package lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Token;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Pengajar;
import lombok.Data;

@Entity
@Data
@Table(name = "pengajar_token")
public class PengajarToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pengajar_id")
    private Pengajar pengajar;

    @Column(nullable=false)
    private LocalDateTime createdAt;
}
