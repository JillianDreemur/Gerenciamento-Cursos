package appGerente.GerenciamentoCursos.services;

import appGerente.GerenciamentoCursos.dao.UsuarioDAO;
import appGerente.GerenciamentoCursos.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> findAll() {
        return usuarioDAO.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));
    }

    public Usuario findByLogin(String login) {
        return usuarioDAO.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com login: " + login));
    }

    public Usuario save(Usuario usuario) {
        if (usuario.getLogin() == null || usuario.getLogin().isEmpty()) {
            throw new RuntimeException("Login é obrigatório");
        }
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            throw new RuntimeException("Senha é obrigatória");
        }
        // Criptografa a senha se ainda não estiver criptografada
        if (!usuario.getSenha().startsWith("$2a$")) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        return usuarioDAO.save(usuario);
    }

    public Usuario update(Long id, Usuario usuario) {
        Usuario existing = findById(id);
        usuario.setId(existing.getId());
        // Se a senha foi alterada, criptografa
        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty() && !usuario.getSenha().startsWith("$2a$")) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        } else if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            // Mantém a senha existente se não foi fornecida
            usuario.setSenha(existing.getSenha());
        }
        return usuarioDAO.save(usuario);
    }

    public void deleteById(Long id) {
        findById(id); // Verifica se existe
        usuarioDAO.deleteById(id);
    }
}

