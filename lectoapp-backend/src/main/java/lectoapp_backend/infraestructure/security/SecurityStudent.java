package lectoapp_backend.infraestructure.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lectoapp_backend.domain.model.Estudiante;

public class SecurityStudent implements UserDetails {

    private final Estudiante estudiante;

    public SecurityStudent(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_ESTUDIANTE")
        );
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return estudiante.getCodigoAcceso();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(estudiante.getActivo());
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

}
