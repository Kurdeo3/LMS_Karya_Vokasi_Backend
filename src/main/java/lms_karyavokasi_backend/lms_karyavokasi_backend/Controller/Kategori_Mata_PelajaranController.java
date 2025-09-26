package lms_karyavokasi_backend.lms_karyavokasi_backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Kategori_Mata_Pelajaran.Kategori_Mata_PelajaranRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Kategori_Mata_Pelajaran.Kategori_Mata_PelajaranResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.Kategori_Mata_PelajaranService;

@RestController
@RequestMapping("/kategori-matapelajaran")
@CrossOrigin("http://localhost:3000")
public class Kategori_Mata_PelajaranController {
    @Autowired
    private Kategori_Mata_PelajaranService kategoriService;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping
    public Kategori_Mata_PelajaranResponse createKategoriMataPelajaran(@RequestBody Kategori_Mata_PelajaranRequest request) {
        return kategoriService.createKategori_MataPelajaran(request);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/{id}")
    public Kategori_Mata_PelajaranResponse updateKategoriMataPelajaran(@PathVariable Long id, @RequestBody Kategori_Mata_PelajaranRequest request) {
        return kategoriService.updateKategori_MataPelajaran(id, request);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public String deleteKategoriMataPelajaran(@PathVariable Long id) {
        kategoriService.deleteKategori_MataPelajaran(id);
        return "KategoriE_Katalog dengan id " + id + " berhasil dihapus";
    }

    @GetMapping
    public List<Kategori_Mata_PelajaranResponse> getAllKategoriMataPelajaran() {
        return kategoriService.getAllKategori_MataPelajaran();
    }

    @GetMapping("/{id}")
    public Kategori_Mata_PelajaranResponse getKategoriByIdMataPelajaran(@PathVariable Long id) {
        return kategoriService.getByIdKategori_MataPelajaran(id);
    }
}
