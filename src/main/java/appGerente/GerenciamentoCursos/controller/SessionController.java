package appGerente.GerenciamentoCursos.controller;

import appGerente.GerenciamentoCursos.models.Session;
import appGerente.GerenciamentoCursos.models.dto.ApiResponse;
import appGerente.GerenciamentoCursos.services.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@Tag(name = "Sessões", description = "Endpoints para gerenciamento de sessões")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping
    @Operation(summary = "Listar todas as sessões")
    public ResponseEntity<ApiResponse<List<Session>>> findAll() {
        List<Session> sessions = sessionService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(sessions));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sessão por ID")
    public ResponseEntity<ApiResponse<Session>> findById(@PathVariable Long id) {
        Session session = sessionService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(session));
    }

    @GetMapping("/aluno/{alunoId}")
    @Operation(summary = "Buscar sessões por ID do aluno")
    public ResponseEntity<ApiResponse<List<Session>>> findByAlunoId(@PathVariable Long alunoId) {
        List<Session> sessions = sessionService.findByAlunoId(alunoId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(sessions));
    }

    @GetMapping("/curso/{cursoId}")
    @Operation(summary = "Buscar sessões por ID do curso")
    public ResponseEntity<ApiResponse<List<Session>>> findByCursoId(@PathVariable Long cursoId) {
        List<Session> sessions = sessionService.findByCursoId(cursoId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(sessions));
    }

    @PostMapping
    @Operation(summary = "Criar nova sessão")
    public ResponseEntity<ApiResponse<Session>> create(@RequestBody Session session) {
        Session saved = sessionService.save(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Sessão criada com sucesso", saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar sessão")
    public ResponseEntity<ApiResponse<Session>> update(@PathVariable Long id, @RequestBody Session session) {
        Session updated = sessionService.update(id, session);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Sessão atualizada com sucesso", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar sessão")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        sessionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Sessão deletada com sucesso", null));
    }
}

