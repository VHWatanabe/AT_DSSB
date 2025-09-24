package com.infnet.AT_DSSB.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

public class AlunoDTOs {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Create {
        @NotBlank private String nome;
        @NotBlank @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos numéricos")
        private String cpf;
        @NotBlank @Email private String email;
        @NotBlank private String telefone;
        @NotBlank private String endereco;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private String id;
        private String nome;
        private String cpf;
        private String email;
        private String telefone;
        private String endereco;
    }
}
