package appGerente.GerenciamentoCursos.services;

import appGerente.GerenciamentoCursos.dao.CursoDAO;
import appGerente.GerenciamentoCursos.models.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoDAO cursoDAO;

    public List<Curso> findAll() {
        return cursoDAO.findAll();
    }

    public Curso findById(Long id) {
        return cursoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com id: " + id));
    }

    public Curso save(Curso curso) {
        if (curso.getNome() == null || curso.getNome().isEmpty()) {
            throw new RuntimeException("Nome é obrigatório");
        }
        if (curso.getStatus() == null || curso.getStatus().isEmpty()) {
            curso.setStatus("ATIVO");
        }
        return cursoDAO.save(curso);
    }

    public Curso update(Long id, Curso curso) {
        Curso existing = findById(id);
        curso.setId(existing.getId());
        return cursoDAO.save(curso);
    }

    public void deleteById(Long id) {
        findById(id); // Verifica se existe
        cursoDAO.deleteById(id);
    }
}

