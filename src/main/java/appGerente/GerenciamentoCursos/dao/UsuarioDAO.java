package appGerente.GerenciamentoCursos.dao;

import appGerente.GerenciamentoCursos.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Usuario> rowMapper = (rs, rowNum) -> {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setLogin(rs.getString("login"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setRole(rs.getString("role"));
        return usuario;
    };

    public List<Usuario> findAll() {
        String sql = "SELECT * FROM usuarios";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Usuario> findById(Long id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        List<Usuario> usuarios = jdbcTemplate.query(sql, rowMapper, id);
        return usuarios.isEmpty() ? Optional.empty() : Optional.of(usuarios.get(0));
    }

    public Optional<Usuario> findByLogin(String login) {
        String sql = "SELECT * FROM usuarios WHERE login = ?";
        List<Usuario> usuarios = jdbcTemplate.query(sql, rowMapper, login);
        return usuarios.isEmpty() ? Optional.empty() : Optional.of(usuarios.get(0));
    }

    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            String sql = "INSERT INTO usuarios (login, senha, nome, email, role) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, usuario.getLogin(), usuario.getSenha(), usuario.getNome(), 
                    usuario.getEmail(), usuario.getRole());
            return findByLogin(usuario.getLogin()).orElse(usuario);
        } else {
            String sql = "UPDATE usuarios SET login = ?, senha = ?, nome = ?, email = ?, role = ? WHERE id = ?";
            jdbcTemplate.update(sql, usuario.getLogin(), usuario.getSenha(), usuario.getNome(), 
                    usuario.getEmail(), usuario.getRole(), usuario.getId());
            return usuario;
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

