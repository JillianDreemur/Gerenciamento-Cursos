package appGerente.GerenciamentoCursos.dao;

import appGerente.GerenciamentoCursos.models.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class SessionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Session> rowMapper = (rs, rowNum) -> {
        Session session = new Session();
        session.setId(rs.getLong("id"));
        session.setAlunoId(rs.getLong("aluno_id"));
        session.setCursoId(rs.getLong("curso_id"));
        Timestamp timestamp = rs.getTimestamp("data_sessao");
        session.setDataSessao(timestamp != null ? timestamp.toLocalDateTime() : null);
        session.setConteudo(rs.getString("conteudo"));
        session.setDuracaoMinutos(rs.getInt("duracao_minutos"));
        session.setStatus(rs.getString("status"));
        return session;
    };

    public List<Session> findAll() {
        String sql = "SELECT * FROM sessions";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Session> findById(Long id) {
        String sql = "SELECT * FROM sessions WHERE id = ?";
        List<Session> sessions = jdbcTemplate.query(sql, rowMapper, id);
        return sessions.isEmpty() ? Optional.empty() : Optional.of(sessions.get(0));
    }

    public List<Session> findByAlunoId(Long alunoId) {
        String sql = "SELECT * FROM sessions WHERE aluno_id = ?";
        return jdbcTemplate.query(sql, rowMapper, alunoId);
    }

    public List<Session> findByCursoId(Long cursoId) {
        String sql = "SELECT * FROM sessions WHERE curso_id = ?";
        return jdbcTemplate.query(sql, rowMapper, cursoId);
    }

    public Session save(Session session) {
        if (session.getId() == null) {
            String sql = "INSERT INTO sessions (aluno_id, curso_id, data_sessao, conteudo, duracao_minutos, status) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, session.getAlunoId(), session.getCursoId(),
                    session.getDataSessao() != null ? Timestamp.valueOf(session.getDataSessao()) : null,
                    session.getConteudo(), session.getDuracaoMinutos(), session.getStatus());
            // Buscar a sessão inserida
            String selectSql = "SELECT * FROM sessions WHERE aluno_id = ? AND curso_id = ? ORDER BY id DESC LIMIT 1";
            List<Session> sessions = jdbcTemplate.query(selectSql, rowMapper, session.getAlunoId(), session.getCursoId());
            return sessions.isEmpty() ? session : sessions.get(0);
        } else {
            String sql = "UPDATE sessions SET aluno_id = ?, curso_id = ?, data_sessao = ?, conteudo = ?, duracao_minutos = ?, status = ? WHERE id = ?";
            jdbcTemplate.update(sql, session.getAlunoId(), session.getCursoId(),
                    session.getDataSessao() != null ? Timestamp.valueOf(session.getDataSessao()) : null,
                    session.getConteudo(), session.getDuracaoMinutos(), session.getStatus(), session.getId());
            return session;
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM sessions WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

