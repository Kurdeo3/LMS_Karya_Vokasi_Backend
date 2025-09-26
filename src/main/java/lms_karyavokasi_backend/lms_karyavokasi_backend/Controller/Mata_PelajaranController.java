package lms_karyavokasi_backend.lms_karyavokasi_backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Mata_Pelajaran.Mata_PelajaranRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Mata_Pelajaran.Mata_PelajaranResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.Mata_PelajaranService;

@RestController
@RequestMapping("/mata-pelajaran")
public class Mata_PelajaranController {
    @Autowired
    private Mata_PelajaranService mataPelajaranService;

    @PreAuthorize("hasRole('PENGAJAR')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mata_PelajaranResponse create(@RequestPart("data") Mata_PelajaranRequest request,
        @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) {
        return mataPelajaranService.createMataPelajaran(request, thumbnail);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mata_PelajaranResponse update(@PathVariable Long id, @RequestPart("data") Mata_PelajaranRequest request,
        @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) {
        return mataPelajaranService.updateMataPelajaran(id, request, thumbnail);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        mataPelajaranService.deleteMataPelajaran(id);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @GetMapping("/{id}")
    public Mata_PelajaranResponse getById(@PathVariable Long id) {
        return mataPelajaranService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @GetMapping
    public List<Mata_PelajaranResponse> getAll() {
        return mataPelajaranService.getAll();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @GetMapping("/by-ekatalog/{eKatalogId}")
    public List<Mata_PelajaranResponse> getByEKatalog(@PathVariable Long eKatalogId) {
        return mataPelajaranService.getByEKatalog(eKatalogId);
    }
}
