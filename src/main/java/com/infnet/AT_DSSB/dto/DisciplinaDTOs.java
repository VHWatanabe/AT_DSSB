package com.infnet.AT_DSSB.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class DisciplinaDTOs {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Create {
        @NotBlank private String codigo;
        @NotBlank private String nome;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private String id;
        private String codigo;
        private String nome;
    }
}