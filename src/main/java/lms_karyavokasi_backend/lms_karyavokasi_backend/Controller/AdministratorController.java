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

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Administrator.AdministratorRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Administrator.AdministratorResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.AdministratorService;

@RestController
@RequestMapping("/administrator")
@PreAuthorize("hasRole('ADMINISTRATOR')")
@CrossOrigin("http://localhost:3000")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    // CREATE
    @PostMapping
    public AdministratorResponse createAdministrator(@RequestBody AdministratorRequest request) {
        return administratorService.createAdministrator(request);
    }

    // READ ALL
    @GetMapping
    public List<AdministratorResponse> getAllAdministrator() {
        return administratorService.getAllAdministrator();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public AdministratorResponse getAdministratorById(@PathVariable Long id) {
        return administratorService.getAdministratorById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public AdministratorResponse updateAdministrator(@PathVariable Long id, @RequestBody AdministratorRequest request) {
        return administratorService.updateAdministrator(request, id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteAdministrator(@PathVariable Long id) {
        administratorService.deleteAdministrator(id);
        return "Administrator with id " + id + " has been deleted";
    }
}
