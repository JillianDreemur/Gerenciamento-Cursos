package appGerente.GerenciamentoCursos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private Long id;
    private Long alunoId;
    private Long cursoId;
    private LocalDateTime dataSessao;
    private String conteudo;
    private Integer duracaoMinutos;
    private String status; // AGENDADA, REALIZADA, CANCELADA
}

