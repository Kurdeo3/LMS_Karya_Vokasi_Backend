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

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.E_Katalog.E_KatalogRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.E_Katalog.E_KatalogResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.E_KatalogService;

@RestController
@RequestMapping("/e-katalog")
@CrossOrigin("http://localhost:3000")
public class E_KatalogController {

    @Autowired
    private E_KatalogService eKatalogService;

    // CREATE (Save as Draft / Lanjut → tergantung status yang dikirim)
    @PreAuthorize("hasRole('PENGAJAR')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public E_KatalogResponse create(@RequestPart("data") E_KatalogRequest request,
        @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) {
        return eKatalogService.createE_Katalog(request, thumbnail);
    }

    // READ ALL
    @PreAuthorize("hasAnyRole('PENGAJAR','ADMINISTRATOR')")
    @GetMapping
    public List<E_KatalogResponse> getAll() {
        return eKatalogService.getAllE_Katalog();
    }

    // READ BY ID
    @PreAuthorize("hasAnyRole('PENGAJAR','ADMINISTRATOR')")
    @GetMapping("/{id}")
    public E_KatalogResponse getById(@PathVariable Long id) {
        return eKatalogService.getByIdE_Katalog(id);
    }

    // UPDATE
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public E_KatalogResponse update(@PathVariable Long id, @RequestPart("data") E_KatalogRequest request,
        @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) {
        return eKatalogService.updateE_Katalog(id, request, thumbnail);
    }

    // DELETE
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        eKatalogService.deleteE_Katalog(id);
        return "E_Katalog with id " + id + " has been deleted";
    }
}
