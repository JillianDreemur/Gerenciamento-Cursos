package appGerente.GerenciamentoCursos.dao;

import appGerente.GerenciamentoCursos.models.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class CursoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Curso> rowMapper = (rs, rowNum) -> {
        Curso curso = new Curso();
        curso.setId(rs.getLong("id"));
        curso.setNome(rs.getString("nome"));
        curso.setDescricao(rs.getString("descricao"));
        curso.setCargaHoraria(rs.getInt("carga_horaria"));
        Date dataInicio = rs.getDate("data_inicio");
        Date dataFim = rs.getDate("data_fim");
        curso.setDataInicio(dataInicio != null ? dataInicio.toLocalDate() : null);
        curso.setDataFim(dataFim != null ? dataFim.toLocalDate() : null);
        curso.setStatus(rs.getString("status"));
        return curso;
    };

    public List<Curso> findAll() {
        String sql = "SELECT * FROM cursos";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Curso> findById(Long id) {
        String sql = "SELECT * FROM cursos WHERE id = ?";
        List<Curso> cursos = jdbcTemplate.query(sql, rowMapper, id);
        return cursos.isEmpty() ? Optional.empty() : Optional.of(cursos.get(0));
    }

    public Curso save(Curso curso) {
        if (curso.getId() == null) {
            String sql = "INSERT INTO cursos (nome, descricao, carga_horaria, data_inicio, data_fim, status) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, curso.getNome(), curso.getDescricao(), curso.getCargaHoraria(),
                    curso.getDataInicio() != null ? Date.valueOf(curso.getDataInicio()) : null,
                    curso.getDataFim() != null ? Date.valueOf(curso.getDataFim()) : null,
                    curso.getStatus());
            // Buscar o curso inserido
            String selectSql = "SELECT * FROM cursos WHERE nome = ? ORDER BY id DESC LIMIT 1";
            List<Curso> cursos = jdbcTemplate.query(selectSql, rowMapper, curso.getNome());
            return cursos.isEmpty() ? curso : cursos.get(0);
        } else {
            String sql = "UPDATE cursos SET nome = ?, descricao = ?, carga_horaria = ?, data_inicio = ?, data_fim = ?, status = ? WHERE id = ?";
            jdbcTemplate.update(sql, curso.getNome(), curso.getDescricao(), curso.getCargaHoraria(),
                    curso.getDataInicio() != null ? Date.valueOf(curso.getDataInicio()) : null,
                    curso.getDataFim() != null ? Date.valueOf(curso.getDataFim()) : null,
                    curso.getStatus(), curso.getId());
            return curso;
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM cursos WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

