package appGerente.GerenciamentoCursos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nota {
    private Long id;
    private Long alunoId;
    private Long cursoId;
    private Double valor;
    private String tipoAvaliacao; // PROVA, TRABALHO, PROJETO
    private LocalDate dataAvaliacao;
    private String observacoes;
}

