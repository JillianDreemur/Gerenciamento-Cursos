package appGerente.GerenciamentoCursos.services;

import appGerente.GerenciamentoCursos.dao.UsuarioDAO;
import appGerente.GerenciamentoCursos.models.Usuario;
import appGerente.GerenciamentoCursos.models.dto.LoginRequest;
import appGerente.GerenciamentoCursos.models.dto.LoginResponse;
import appGerente.GerenciamentoCursos.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest) {
        Usuario usuario = usuarioDAO.findByLogin(loginRequest.getLogin())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        // Verifica senha com BCrypt ou senha em texto plano (para compatibilidade)
        if (!passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha()) 
                && !usuario.getSenha().equals(loginRequest.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        String token = jwtTokenProvider.generateToken(usuario.getLogin(), usuario.getRole());

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setTipo("Bearer");
        response.setLogin(usuario.getLogin());
        response.setNome(usuario.getNome());
        response.setRole(usuario.getRole());

        return response;
    }
}

