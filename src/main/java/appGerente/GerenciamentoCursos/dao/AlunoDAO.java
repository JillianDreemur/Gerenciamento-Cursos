package appGerente.GerenciamentoCursos.dao;

import appGerente.GerenciamentoCursos.models.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AlunoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Aluno> rowMapper = (rs, rowNum) -> {
        Aluno aluno = new Aluno();
        aluno.setId(rs.getLong("id"));
        aluno.setNome(rs.getString("nome"));
        aluno.setEmail(rs.getString("email"));
        aluno.setCpf(rs.getString("cpf"));
        aluno.setTelefone(rs.getString("telefone"));
        return aluno;
    };

    public List<Aluno> findAll() {
        String sql = "SELECT * FROM alunos";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Aluno> findById(Long id) {
        String sql = "SELECT * FROM alunos WHERE id = ?";
        List<Aluno> alunos = jdbcTemplate.query(sql, rowMapper, id);
        return alunos.isEmpty() ? Optional.empty() : Optional.of(alunos.get(0));
    }

    public Aluno save(Aluno aluno) {
        if (aluno.getId() == null) {
            String sql = "INSERT INTO alunos (nome, email, cpf, telefone) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, aluno.getNome(), aluno.getEmail(), aluno.getCpf(), aluno.getTelefone());
            // Buscar o aluno inserido
            String selectSql = "SELECT * FROM alunos WHERE cpf = ?";
            List<Aluno> alunos = jdbcTemplate.query(selectSql, rowMapper, aluno.getCpf());
            return alunos.isEmpty() ? aluno : alunos.get(0);
        } else {
            String sql = "UPDATE alunos SET nome = ?, email = ?, cpf = ?, telefone = ? WHERE id = ?";
            jdbcTemplate.update(sql, aluno.getNome(), aluno.getEmail(), aluno.getCpf(), 
                    aluno.getTelefone(), aluno.getId());
            return aluno;
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM alunos WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

