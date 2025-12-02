package appGerente.GerenciamentoCursos.controller;

import appGerente.GerenciamentoCursos.models.Aluno;
import appGerente.GerenciamentoCursos.models.dto.ApiResponse;
import appGerente.GerenciamentoCursos.services.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@Tag(name = "Alunos", description = "Endpoints para gerenciamento de alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    @Operation(summary = "Listar todos os alunos")
    public ResponseEntity<ApiResponse<List<Aluno>>> findAll() {
        List<Aluno> alunos = alunoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(alunos));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar aluno por ID")
    public ResponseEntity<ApiResponse<Aluno>> findById(@PathVariable Long id) {
        Aluno aluno = alunoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(aluno));
    }

    @PostMapping
    @Operation(summary = "Criar novo aluno")
    public ResponseEntity<ApiResponse<Aluno>> create(@RequestBody Aluno aluno) {
        Aluno saved = alunoService.save(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Aluno criado com sucesso", saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar aluno")
    public ResponseEntity<ApiResponse<Aluno>> update(@PathVariable Long id, @RequestBody Aluno aluno) {
        Aluno updated = alunoService.update(id, aluno);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Aluno atualizado com sucesso", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar aluno")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        alunoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Aluno deletado com sucesso", null));
    }
}

