package lms_karyavokasi_backend.lms_karyavokasi_backend.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Administrator.AdministratorRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Administrator.AdministratorResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.BadRequestException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.NotFoundException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Administrator;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.AdministratorRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.AdministratorService;

@Service
public class AdministratorServiceImpl implements AdministratorService{
    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private AdministratorResponse convertToResponse (Administrator admin){
        AdministratorResponse response = new AdministratorResponse();
        response.setId(admin.getId());
        response.setNama(admin.getNama());
        response.setEmail(admin.getEmail());
        return response;
    }

    @Override
    public AdministratorResponse createAdministrator(AdministratorRequest request) {
        if (request.getNama() == null || request.getNama().isBlank()) {
            throw new BadRequestException("Nama wajib diisi");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new BadRequestException("Email wajib diisi");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new BadRequestException("Password wajib diisi");
        }

        Administrator admin = new Administrator();
        admin.setNama(request.getNama());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));

        return convertToResponse(administratorRepository.save(admin));
    }

    @Override
    public List<AdministratorResponse> getAllAdministrator() {
        return administratorRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AdministratorResponse getAdministratorById(Long id) {
        Administrator admin = administratorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Administrator", id));
        return convertToResponse(admin);
    }

    @Override
    public AdministratorResponse updateAdministrator(AdministratorRequest request, Long id) {
        Administrator admin = administratorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Administrator", id));

        // Hanya update jika field dikirim dan tidak null
        if (request.getNama() != null) {
            admin.setNama(request.getNama());
        }

        if (request.getEmail() != null) {
            // Pastikan email tidak kosong string
            if (request.getEmail().isBlank()) {
                throw new IllegalArgumentException("Email tidak boleh kosong");
            }
            admin.setEmail(request.getEmail());
        }

        if (request.getPassword() != null) {
            if (request.getPassword().isBlank()) {
                throw new IllegalArgumentException("Password tidak boleh kosong");
            }
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return convertToResponse(administratorRepository.save(admin));
    }

    @Override
    public void deleteAdministrator(Long id) {
        if (!administratorRepository.existsById(id)) {
                throw new NotFoundException("Administrator", id);
        }
        administratorRepository.deleteById(id);
    }
    
}
