package com.infnet.AT_DSSB.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matriculas")
@CompoundIndex(name = "aluno_disciplina_idx", def = "{'alunoId': 1, 'disciplinaId': 1}", unique = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Matricula {
    @Id
    private String id;

    private String alunoId;
    private String disciplinaId;
    private Double nota;
}
