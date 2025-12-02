package appGerente.GerenciamentoCursos.services;

import appGerente.GerenciamentoCursos.dao.SessionDAO;
import appGerente.GerenciamentoCursos.models.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    @Autowired
    private SessionDAO sessionDAO;

    public List<Session> findAll() {
        return sessionDAO.findAll();
    }

    public Session findById(Long id) {
        return sessionDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada com id: " + id));
    }

    public List<Session> findByAlunoId(Long alunoId) {
        return sessionDAO.findByAlunoId(alunoId);
    }

    public List<Session> findByCursoId(Long cursoId) {
        return sessionDAO.findByCursoId(cursoId);
    }

    public Session save(Session session) {
        if (session.getAlunoId() == null) {
            throw new RuntimeException("ID do aluno é obrigatório");
        }
        if (session.getCursoId() == null) {
            throw new RuntimeException("ID do curso é obrigatório");
        }
        if (session.getDataSessao() == null) {
            throw new RuntimeException("Data da sessão é obrigatória");
        }
        if (session.getStatus() == null || session.getStatus().isEmpty()) {
            session.setStatus("AGENDADA");
        }
        return sessionDAO.save(session);
    }

    public Session update(Long id, Session session) {
        Session existing = findById(id);
        session.setId(existing.getId());
        return sessionDAO.save(session);
    }

    public void deleteById(Long id) {
        findById(id); // Verifica se existe
        sessionDAO.deleteById(id);
    }
}

