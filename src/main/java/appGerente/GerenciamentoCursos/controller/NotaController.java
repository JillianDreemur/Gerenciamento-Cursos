package appGerente.GerenciamentoCursos.controller;

import appGerente.GerenciamentoCursos.models.Nota;
import appGerente.GerenciamentoCursos.models.dto.ApiResponse;
import appGerente.GerenciamentoCursos.services.NotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
@Tag(name = "Notas", description = "Endpoints para gerenciamento de notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @GetMapping
    @Operation(summary = "Listar todas as notas")
    public ResponseEntity<ApiResponse<List<Nota>>> findAll() {
        List<Nota> notas = notaService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(notas));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar nota por ID")
    public ResponseEntity<ApiResponse<Nota>> findById(@PathVariable Long id) {
        Nota nota = notaService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(nota));
    }

    @GetMapping("/aluno/{alunoId}")
    @Operation(summary = "Buscar notas por ID do aluno")
    public ResponseEntity<ApiResponse<List<Nota>>> findByAlunoId(@PathVariable Long alunoId) {
        List<Nota> notas = notaService.findByAlunoId(alunoId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(notas));
    }

    @GetMapping("/curso/{cursoId}")
    @Operation(summary = "Buscar notas por ID do curso")
    public ResponseEntity<ApiResponse<List<Nota>>> findByCursoId(@PathVariable Long cursoId) {
        List<Nota> notas = notaService.findByCursoId(cursoId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(notas));
    }

    @PostMapping
    @Operation(summary = "Criar nova nota")
    public ResponseEntity<ApiResponse<Nota>> create(@RequestBody Nota nota) {
        Nota saved = notaService.save(nota);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Nota criada com sucesso", saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar nota")
    public ResponseEntity<ApiResponse<Nota>> update(@PathVariable Long id, @RequestBody Nota nota) {
        Nota updated = notaService.update(id, nota);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Nota atualizada com sucesso", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar nota")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        notaService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Nota deletada com sucesso", null));
    }
}

