package lms_karyavokasi_backend.lms_karyavokasi_backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Teks.Materi_TeksRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Teks.Materi_TeksResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.Materi_TeksService;

@RestController
@RequestMapping("/materi_teks")
@CrossOrigin("http://localhost:3000")
public class Materi_TeksController {

    @Autowired
    private Materi_TeksService materiTeksService;

    @PreAuthorize("hasRole('PENGAJAR')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Materi_TeksResponse create(@RequestPart("data") Materi_TeksRequest request,
        @RequestPart("file") MultipartFile file) {
        return materiTeksService.create(request, file);
    }

    @PreAuthorize("hasRole('PENGAJAR')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Materi_TeksResponse update(@PathVariable Long id, @RequestPart("data") Materi_TeksRequest request,
        @RequestPart(value="file", required=false) MultipartFile file) {
        return materiTeksService.update(id, request, file);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @GetMapping("/topik/{topikId}")
    public List<Materi_TeksResponse> getByTopik(@PathVariable Long topikId) {
        return materiTeksService.getByTopik(topikId);
    }

    @PreAuthorize("hasAnyRole('PENGAJAR', 'ADMINISTRATOR')")
    @GetMapping()
    public List<Materi_TeksResponse> getAll() {
        return materiTeksService.getAll();
    }

    @PreAuthorize("hasAnyRole('PENGAJAR', 'ADMINISTRATOR')")
    @GetMapping("/{id}")
    public Materi_TeksResponse getById(@PathVariable long id) {
        return materiTeksService.getById(id);
    }

    // DELETE
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        materiTeksService.delete(id);
        return "Materi Teks with id " + id + " has been deleted";
    }
    
}
