package lms_karyavokasi_backend.lms_karyavokasi_backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Topik_Mata_Pelajaran.Topik_Mata_PelajaranRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Topik_Mata_Pelajaran.Topik_Mata_PelajaranResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.Topik_Mata_PelajaranService;

@RestController
@RequestMapping("/topik")
public class Topik_Mata_PelajaranController {
    @Autowired
    private Topik_Mata_PelajaranService topikService;

    @PreAuthorize("hasRole('PENGAJAR')")
    @PostMapping
    public Topik_Mata_PelajaranResponse create(@RequestBody Topik_Mata_PelajaranRequest request) {
        return topikService.createTopik(request);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @PutMapping("/{id}")
    public Topik_Mata_PelajaranResponse update(@PathVariable Long id, @RequestBody Topik_Mata_PelajaranRequest request) {
        return topikService.updateTopik(id, request);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        topikService.deleteTopik(id);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @GetMapping("/{id}")
    public Topik_Mata_PelajaranResponse getById(@PathVariable Long id) {
        return topikService.getByIdTopik(id);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @GetMapping
    public List<Topik_Mata_PelajaranResponse> getAll() {
        return topikService.getAllTopik();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @GetMapping("/by-ekatalog/{eKatalogId}")
    public List<Topik_Mata_PelajaranResponse> getByEKatalog(@PathVariable Long mataPelajaranId) {
        return topikService.getByMataPelajaran(mataPelajaranId);
    }
}
