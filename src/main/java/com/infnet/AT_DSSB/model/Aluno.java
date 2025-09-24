package com.infnet.AT_DSSB.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "alunos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Aluno {
    @Id
    private String id;

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String endereco;
}
