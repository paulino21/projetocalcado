package br.com.projetocalcado.controller;

import br.com.projetocalcado.domain.Usuario.DadosAutenticacao;
import br.com.projetocalcado.domain.Usuario.DadosCadastraLogin;
import br.com.projetocalcado.domain.Usuario.User;
import br.com.projetocalcado.domain.Usuario.UserRepository;
import br.com.projetocalcado.infra.security.DadosToken;
import br.com.projetocalcado.infra.security.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid DadosAutenticacao dados) {

        var userNamePassword = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = this.authenticationManager.authenticate(userNamePassword);
        var acess_token = tokenService.gerartoken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosToken(acess_token));
    }

    @PostMapping("/registro")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity cadastraLogin(@RequestBody @Valid DadosCadastraLogin dados) {

        if (userRepository.findByLogin(dados.login()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(dados.senha());
        User usuario = new User(dados.login(), encryptedPassword, dados.role());
        userRepository.save(usuario);

        return ResponseEntity.ok().build();

    }


}
