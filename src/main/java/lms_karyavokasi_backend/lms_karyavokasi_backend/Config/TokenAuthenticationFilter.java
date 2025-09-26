package lms_karyavokasi_backend.lms_karyavokasi_backend.Config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Token.AdministratorToken;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Token.PengajarToken;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.TokenRepository.AdministratorTokenRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.TokenRepository.PengajarTokenRepository;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AdministratorTokenRepository adminTokenRepository;

    @Autowired
    private PengajarTokenRepository pengajarTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        // Cek token untuk Admin
        Optional<AdministratorToken> adminToken = adminTokenRepository.findByToken(token);
        if (adminToken.isPresent()) {
            // ambil email tanpa trigger lazy (pastikan kolom email di Administrator bukan LAZY)
            String email = adminToken.get().getAdministrator().getEmail();

            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                    email, // pakai String email, bukan entity
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"))
                );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            return;
        }

        // Cek token untuk Pengajar
        Optional<PengajarToken> pengajarToken = pengajarTokenRepository.findByToken(token);
        if (pengajarToken.isPresent()) {
            // 👇 solusinya: jangan akses entity langsung
            // ambil id/email via query atau pastikan relasi EAGER
            String email = pengajarToken.get().getPengajar().getEmail();

            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_PENGAJAR"))
                );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            return;
        }


        // Kalau tidak valid, lanjutkan tanpa auth
        filterChain.doFilter(request, response);
    }
}

