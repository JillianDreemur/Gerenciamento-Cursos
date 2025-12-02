package appGerente.GerenciamentoCursos.controller;

import appGerente.GerenciamentoCursos.models.Usuario;
import appGerente.GerenciamentoCursos.models.dto.ApiResponse;
import appGerente.GerenciamentoCursos.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public ResponseEntity<ApiResponse<List<Usuario>>> findAll() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(usuarios));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<ApiResponse<Usuario>> findById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(usuario));
    }

    @PostMapping
    @Operation(summary = "Criar novo usuário")
    public ResponseEntity<ApiResponse<Usuario>> create(@RequestBody Usuario usuario) {
        Usuario saved = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Usuário criado com sucesso", saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    public ResponseEntity<ApiResponse<Usuario>> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario updated = usuarioService.update(id, usuario);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Usuário atualizado com sucesso", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Usuário deletado com sucesso", null));
    }
}

