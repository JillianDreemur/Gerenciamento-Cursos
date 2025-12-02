package appGerente.GerenciamentoCursos.services;

import appGerente.GerenciamentoCursos.dao.NotaDAO;
import appGerente.GerenciamentoCursos.models.Nota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaService {

    @Autowired
    private NotaDAO notaDAO;

    public List<Nota> findAll() {
        return notaDAO.findAll();
    }

    public Nota findById(Long id) {
        return notaDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrada com id: " + id));
    }

    public List<Nota> findByAlunoId(Long alunoId) {
        return notaDAO.findByAlunoId(alunoId);
    }

    public List<Nota> findByCursoId(Long cursoId) {
        return notaDAO.findByCursoId(cursoId);
    }

    public Nota save(Nota nota) {
        if (nota.getAlunoId() == null) {
            throw new RuntimeException("ID do aluno é obrigatório");
        }
        if (nota.getCursoId() == null) {
            throw new RuntimeException("ID do curso é obrigatório");
        }
        if (nota.getValor() == null) {
            throw new RuntimeException("Valor da nota é obrigatório");
        }
        if (nota.getDataAvaliacao() == null) {
            nota.setDataAvaliacao(java.time.LocalDateTime.now());
        }
        return notaDAO.save(nota);
    }

    public Nota update(Long id, Nota nota) {
        Nota existing = findById(id);
        nota.setId(existing.getId());
        return notaDAO.save(nota);
    }

    public void deleteById(Long id) {
        findById(id); // Verifica se existe
        notaDAO.deleteById(id);
    }
}

