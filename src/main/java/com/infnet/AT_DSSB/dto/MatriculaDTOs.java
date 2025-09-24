package com.infnet.AT_DSSB.dto;

import jakarta.validation.constraints.*;
import lombok.*;

public class MatriculaDTOs {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Enroll {
        @NotBlank private String alunoId;
        @NotBlank private String disciplinaId;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class SetNota {
        @NotBlank private String alunoId;
        @NotBlank private String disciplinaId;

        @NotNull @PositiveOrZero @Max(10)
        private Double nota;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private String id;
        private String alunoId;
        private String alunoNome;
        private String disciplinaId;
        private Double nota;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Resultado {
        private String alunoId;
        private String alunoNome;
        private Double nota;
    }
}