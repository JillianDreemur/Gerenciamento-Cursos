package appGerente.GerenciamentoCursos.controller;

import appGerente.GerenciamentoCursos.models.Curso;
import appGerente.GerenciamentoCursos.models.dto.ApiResponse;
import appGerente.GerenciamentoCursos.services.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@Tag(name = "Cursos", description = "Endpoints para gerenciamento de cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    @Operation(summary = "Listar todos os cursos")
    public ResponseEntity<ApiResponse<List<Curso>>> findAll() {
        List<Curso> cursos = cursoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(cursos));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar curso por ID")
    public ResponseEntity<ApiResponse<Curso>> findById(@PathVariable Long id) {
        Curso curso = cursoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(curso));
    }

    @PostMapping
    @Operation(summary = "Criar novo curso")
    public ResponseEntity<ApiResponse<Curso>> create(@RequestBody Curso curso) {
        Curso saved = cursoService.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Curso criado com sucesso", saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar curso")
    public ResponseEntity<ApiResponse<Curso>> update(@PathVariable Long id, @RequestBody Curso curso) {
        Curso updated = cursoService.update(id, curso);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Curso atualizado com sucesso", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar curso")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        cursoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Curso deletado com sucesso", null));
    }
}

