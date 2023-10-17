package br.com.projetocalcado.domain.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User , Long> {
        UserDetails findByLogin(String login);
}
