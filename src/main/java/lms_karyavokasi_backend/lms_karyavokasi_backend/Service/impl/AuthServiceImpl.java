package lms_karyavokasi_backend.lms_karyavokasi_backend.Service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Login.LoginRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Login.LoginResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.BadRequestException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.NotFoundException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Administrator;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Pengajar;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Token.AdministratorToken;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Token.PengajarToken;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.AdministratorRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.PengajarRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.TokenRepository.AdministratorTokenRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.TokenRepository.PengajarTokenRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private PengajarRepository pengajarRepository;

    @Autowired
    private AdministratorTokenRepository administratorTokenRepository;

    @Autowired
    private PengajarTokenRepository pengajarTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse loginAdministrator(LoginRequest request) {
        Administrator admin = administratorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("Administrator dengan email " + request.getEmail() + " tidak ditemukan"));

        System.out.println("DB password: " + admin.getPassword());
        System.out.println("Raw password: " + request.getPassword());
        System.out.println("Matches? " + passwordEncoder.matches(request.getPassword(), admin.getPassword()));


        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new BadRequestException("Password salah");
        }

        // Generate token
        String token = UUID.randomUUID().toString();

        AdministratorToken adminToken = new AdministratorToken();
        adminToken.setToken(token);
        adminToken.setAdministrator(admin);
        adminToken.setCreatedAt(LocalDateTime.now());

        administratorTokenRepository.save(adminToken);

        LoginResponse response = new LoginResponse();
        response.setUserId(admin.getId());
        response.setNama(admin.getNama());
        response.setEmail(admin.getEmail());
        response.setToken(token);
        response.setRole("Administrator");
        return response;
    }

    @Override
    public LoginResponse loginPengajar(LoginRequest request) {
        Pengajar pengajar = pengajarRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("Pengajar dengan email " + request.getEmail() + " tidak ditemukan"));

        if (!passwordEncoder.matches(request.getPassword(), pengajar.getPassword())) {
            throw new BadRequestException("Password salah");
        }

        // Generate token
        String token = UUID.randomUUID().toString();

        PengajarToken pengajarToken = new PengajarToken();
        pengajarToken.setToken(token);
        pengajarToken.setPengajar(pengajar);
        pengajarToken.setCreatedAt(LocalDateTime.now());

        pengajarTokenRepository.save(pengajarToken);

        LoginResponse response = new LoginResponse();
        response.setUserId(pengajar.getId());
        response.setNama(pengajar.getNama());
        response.setEmail(pengajar.getEmail());
        response.setToken(token);
        response.setRole("Pengajar");
        return response;
    }

    @Override
    @Transactional
    public void logout(String token) {
        administratorTokenRepository.deleteByToken(token);
        pengajarTokenRepository.deleteByToken(token);
    }
}
