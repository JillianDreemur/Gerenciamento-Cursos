package appGerente.GerenciamentoCursos.services;

import appGerente.GerenciamentoCursos.dao.AlunoDAO;
import appGerente.GerenciamentoCursos.models.Aluno;
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

    public Aluno save(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().isEmpty()) {
            throw new RuntimeException("Nome é obrigatório");
        }
        if (aluno.getCpf() == null || aluno.getCpf().isEmpty()) {
            throw new RuntimeException("CPF é obrigatório");
        }
        return alunoDAO.save(aluno);
    }

    public Aluno update(Long id, Aluno aluno) {
        Aluno existing = findById(id);
        aluno.setId(existing.getId());
        return alunoDAO.save(aluno);
    }

    public void deleteById(Long id) {
        findById(id); // Verifica se existe
        alunoDAO.deleteById(id);
    }
}

