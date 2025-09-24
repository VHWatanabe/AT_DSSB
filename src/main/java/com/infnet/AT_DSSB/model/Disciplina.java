package com.infnet.AT_DSSB.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "disciplinas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Disciplina {
    @Id
    private String id;

    @Indexed(unique = true)
    private String codigo;

    private String nome;
}
