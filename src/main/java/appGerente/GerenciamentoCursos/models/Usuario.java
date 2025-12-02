package appGerente.GerenciamentoCursos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Long id;
    private String login;
    private String senha;
    private String nome;
    private String email;
    private String role; // ADMIN, PROFESSOR, ALUNO
}

