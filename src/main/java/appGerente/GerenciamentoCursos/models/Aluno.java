package appGerente.GerenciamentoCursos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
}

