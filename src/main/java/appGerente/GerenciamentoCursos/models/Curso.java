package appGerente.GerenciamentoCursos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    private Long id;
    private String nome;
    private String descricao;
    private Integer cargaHoraria;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String status; // ATIVO, INATIVO, CONCLUIDO
}

