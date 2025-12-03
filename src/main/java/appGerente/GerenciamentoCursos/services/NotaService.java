package appGerente.GerenciamentoCursos.services;

import appGerente.GerenciamentoCursos.dao.CursoDAO;
import appGerente.GerenciamentoCursos.dao.NotaDAO;
import appGerente.GerenciamentoCursos.models.Curso;
import appGerente.GerenciamentoCursos.models.Nota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotaService {

    @Autowired
    private NotaDAO notaDAO;

    @Autowired
    private CursoDAO cursoDAO;

    private static final double NOTA_MINIMA = 0.0;
    private static final double NOTA_MAXIMA = 10.0;

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

    /**
     * Valida os dados da nota antes de salvar
     */
    private void validarNota(Nota nota) {
        // Validação de aluno
        if (nota.getAlunoId() == null) {
            throw new RuntimeException("ID do aluno é obrigatório");
        }

        // Validação de curso
        if (nota.getCursoId() == null) {
            throw new RuntimeException("ID do curso é obrigatório");
        }

        // Buscar o curso para validar as datas
        Curso curso = cursoDAO.findById(nota.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com id: " + nota.getCursoId()));

        // Validação de valor da nota
        if (nota.getValor() == null) {
            throw new RuntimeException("Valor da nota é obrigatório");
        }
        if (nota.getValor() < NOTA_MINIMA || nota.getValor() > NOTA_MAXIMA) {
            throw new IllegalArgumentException(
                    String.format("A nota deve estar entre %.1f e %.1f", NOTA_MINIMA, NOTA_MAXIMA));
        }

        // Validação de data de avaliação
        if (nota.getDataAvaliacao() == null) {
            nota.setDataAvaliacao(LocalDate.now());
        }

        // Validar que a data está dentro do período do curso
        if (curso.getDataInicio() != null && nota.getDataAvaliacao().isBefore(curso.getDataInicio())) {
            throw new IllegalArgumentException(
                    "A data da avaliação não pode ser anterior à data de início do curso (" + curso.getDataInicio() + ")");
        }
        if (curso.getDataFim() != null && nota.getDataAvaliacao().isAfter(curso.getDataFim())) {
            throw new IllegalArgumentException(
                    "A data da avaliação não pode ser posterior à data de fim do curso (" + curso.getDataFim() + ")");
        }
    }

    public Nota save(Nota nota) {
        validarNota(nota);
        return notaDAO.save(nota);
    }

    public Nota update(Long id, Nota nota) {
        Nota existing = findById(id);
        nota.setId(existing.getId());
        validarNota(nota);
        return notaDAO.save(nota);
    }

    public void deleteById(Long id) {
        findById(id); // Verifica se existe
        notaDAO.deleteById(id);
    }
}

