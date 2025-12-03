package appGerente.GerenciamentoCursos.services;

import appGerente.GerenciamentoCursos.dao.AlunoDAO;
import appGerente.GerenciamentoCursos.models.Aluno;
import appGerente.GerenciamentoCursos.utils.FormatadorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoDAO alunoDAO;

    public List<Aluno> findAll() {
        return alunoDAO.findAll();
    }

    public Aluno findById(Long id) {
        return alunoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));
    }

    private void formatarEValidarAluno(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome é obrigatório");
        }
        aluno.setNome(FormatadorUtil.formatarNome(aluno.getNome()));

        if (aluno.getEmail() != null && !aluno.getEmail().trim().isEmpty()) {
            FormatadorUtil.validarEmailGmail(aluno.getEmail());
            aluno.setEmail(aluno.getEmail().toLowerCase().trim());
        }

        if (aluno.getCpf() == null || aluno.getCpf().trim().isEmpty()) {
            throw new RuntimeException("CPF é obrigatório");
        }
        aluno.setCpf(FormatadorUtil.formatarCPF(aluno.getCpf()));

        if (aluno.getTelefone() != null && !aluno.getTelefone().trim().isEmpty()) {
            aluno.setTelefone(FormatadorUtil.formatarTelefone(aluno.getTelefone()));
        }
    }

    public Aluno save(Aluno aluno) {
        formatarEValidarAluno(aluno);
        return alunoDAO.save(aluno);
    }

    public Aluno update(Long id, Aluno aluno) {
        Aluno existing = findById(id);
        aluno.setId(existing.getId());
        formatarEValidarAluno(aluno);
        return alunoDAO.save(aluno);
    }

    public void deleteById(Long id) {
        findById(id);
        alunoDAO.deleteById(id);
    }
}

