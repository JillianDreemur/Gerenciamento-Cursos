package appGerente.GerenciamentoCursos.dao;

import appGerente.GerenciamentoCursos.models.Nota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class NotaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Nota> rowMapper = (rs, rowNum) -> {
        Nota nota = new Nota();
        nota.setId(rs.getLong("id"));
        nota.setAlunoId(rs.getLong("aluno_id"));
        nota.setCursoId(rs.getLong("curso_id"));
        nota.setValor(rs.getDouble("valor"));
        nota.setTipoAvaliacao(rs.getString("tipo_avaliacao"));
        Timestamp timestamp = rs.getTimestamp("data_avaliacao");
        nota.setDataAvaliacao(timestamp != null ? timestamp.toLocalDateTime() : null);
        nota.setObservacoes(rs.getString("observacoes"));
        return nota;
    };

    public List<Nota> findAll() {
        String sql = "SELECT * FROM notas";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Nota> findById(Long id) {
        String sql = "SELECT * FROM notas WHERE id = ?";
        List<Nota> notas = jdbcTemplate.query(sql, rowMapper, id);
        return notas.isEmpty() ? Optional.empty() : Optional.of(notas.get(0));
    }

    public List<Nota> findByAlunoId(Long alunoId) {
        String sql = "SELECT * FROM notas WHERE aluno_id = ?";
        return jdbcTemplate.query(sql, rowMapper, alunoId);
    }

    public List<Nota> findByCursoId(Long cursoId) {
        String sql = "SELECT * FROM notas WHERE curso_id = ?";
        return jdbcTemplate.query(sql, rowMapper, cursoId);
    }

    public Nota save(Nota nota) {
        if (nota.getId() == null) {
            String sql = "INSERT INTO notas (aluno_id, curso_id, valor, tipo_avaliacao, data_avaliacao, observacoes) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, nota.getAlunoId(), nota.getCursoId(), nota.getValor(),
                    nota.getTipoAvaliacao(),
                    nota.getDataAvaliacao() != null ? Timestamp.valueOf(nota.getDataAvaliacao()) : null,
                    nota.getObservacoes());
            // Buscar a nota inserida
            String selectSql = "SELECT * FROM notas WHERE aluno_id = ? AND curso_id = ? ORDER BY id DESC LIMIT 1";
            List<Nota> notas = jdbcTemplate.query(selectSql, rowMapper, nota.getAlunoId(), nota.getCursoId());
            return notas.isEmpty() ? nota : notas.get(0);
        } else {
            String sql = "UPDATE notas SET aluno_id = ?, curso_id = ?, valor = ?, tipo_avaliacao = ?, data_avaliacao = ?, observacoes = ? WHERE id = ?";
            jdbcTemplate.update(sql, nota.getAlunoId(), nota.getCursoId(), nota.getValor(),
                    nota.getTipoAvaliacao(),
                    nota.getDataAvaliacao() != null ? Timestamp.valueOf(nota.getDataAvaliacao()) : null,
                    nota.getObservacoes(), nota.getId());
            return nota;
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM notas WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

