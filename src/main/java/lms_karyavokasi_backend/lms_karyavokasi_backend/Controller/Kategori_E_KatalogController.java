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

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Kategori_E_Katalog.Kategori_E_KatalogRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Kategori_E_Katalog.Kategori_E_KatalogResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.Kategori_E_KatalogService;

@RestController
@RequestMapping("/kategori-ekatalog")
@CrossOrigin("http://localhost:3000")
public class Kategori_E_KatalogController {
    
    @Autowired
    private Kategori_E_KatalogService kategoriService;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping
    public Kategori_E_KatalogResponse createKategoriEKatalog(@RequestBody Kategori_E_KatalogRequest request) {
        return kategoriService.createKategori_EKatalog(request);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/{id}")
    public Kategori_E_KatalogResponse updateKategoriEKatalog(@PathVariable Long id, @RequestBody Kategori_E_KatalogRequest request) {
        return kategoriService.updateKategori_EKatalog(id, request);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public String deleteKategoriEKatalog(@PathVariable Long id) {
        kategoriService.deleteKategori_EKatalog(id);
        return "KategoriE_Katalog dengan id " + id + " berhasil dihapus";
    }

    @GetMapping
    public List<Kategori_E_KatalogResponse> getAllKategoriEKatalog() {
        return kategoriService.getAllKategori_EKatalog();
    }

    @GetMapping("/{id}")
    public Kategori_E_KatalogResponse getKategoriByIdEKatalog(@PathVariable Long id) {
        return kategoriService.getByIdKategori_EKatalog(id);
    }
}
