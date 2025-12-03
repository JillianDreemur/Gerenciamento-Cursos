package appGerente.GerenciamentoCursos.services;

import appGerente.GerenciamentoCursos.dao.CursoDAO;
import appGerente.GerenciamentoCursos.models.Curso;
import appGerente.GerenciamentoCursos.utils.FormatadorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoDAO cursoDAO;

    private static final LocalDate DATA_MINIMA = LocalDate.of(2010, 1, 1);
    private static final LocalDate DATA_MAXIMA = LocalDate.now();

    public List<Curso> findAll() {
        return cursoDAO.findAll();
    }

    public Curso findById(Long id) {
        return cursoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com id: " + id));
    }

    private void formatarEValidarCurso(Curso curso) {
        if (curso.getNome() == null || curso.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome é obrigatório");
        }
        curso.setNome(FormatadorUtil.formatarNome(curso.getNome()));

        if (curso.getDataInicio() != null) {
            if (curso.getDataInicio().isBefore(DATA_MINIMA)) {
                throw new IllegalArgumentException("Data de início não pode ser anterior a 01/01/2010");
            }
            if (curso.getDataInicio().isAfter(DATA_MAXIMA)) {
                throw new IllegalArgumentException("Data de início não pode ser maior que a data atual");
            }
        }

        if (curso.getDataFim() != null) {
            if (curso.getDataFim().isBefore(DATA_MINIMA)) {
                throw new IllegalArgumentException("Data de fim não pode ser anterior a 01/01/2010");
            }
            if (curso.getDataFim().isAfter(DATA_MAXIMA)) {
                throw new IllegalArgumentException("Data de fim não pode ser maior que a data atual");
            }
        }

        if (curso.getDataInicio() != null && curso.getDataFim() != null) {
            if (curso.getDataFim().isBefore(curso.getDataInicio())) {
                throw new IllegalArgumentException("Data de fim deve ser posterior à data de início");
            }
        }

        if (curso.getStatus() == null || curso.getStatus().isEmpty()) {
            curso.setStatus("ATIVO");
        }
    }

    public Curso save(Curso curso) {
        formatarEValidarCurso(curso);
        return cursoDAO.save(curso);
    }

    public Curso update(Long id, Curso curso) {
        Curso existing = findById(id);
        curso.setId(existing.getId());
        formatarEValidarCurso(curso);
        return cursoDAO.save(curso);
    }

    public void deleteById(Long id) {
        findById(id);
        cursoDAO.deleteById(id);
    }
}

