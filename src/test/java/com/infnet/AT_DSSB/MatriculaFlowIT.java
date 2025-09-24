package com.infnet.AT_DSSB;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.AT_DSSB.support.CleanMongoPerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MatriculaFlowIT extends CleanMongoPerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    private static final Map<String, Object> JWT_CLAIMS =
            Map.of("resource_access", Map.of("at-dssb-api", Map.of("roles", List.of("PROFESSOR"))));

    private static RequestPostProcessor jwtProfessor() {
        return jwt()
                .jwt(j -> j.claims(c -> c.putAll(JWT_CLAIMS)))
                .authorities(new SimpleGrantedAuthority("ROLE_PROFESSOR"));
    }

    private static String numeric(int len) {
        var base = String.valueOf(System.nanoTime()) + String.valueOf(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
        var digits = base.replaceAll("\\D", "");
        if (digits.length() < len) digits = digits + "00000000000";
        return digits.substring(0, len);
    }

    private static String uniqueEmail(String prefix) {
        return prefix + "+" + UUID.randomUUID().toString().substring(0, 8) + "@ex.com";
    }

    private static String code() {
        return "COD-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    @Test
    void fluxoCompleto() throws Exception {
        // 1) cria aluno com dados válidos
        var alunoRes = mvc.perform(post("/alunos")
                        .with(jwtProfessor())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of(
                                "nome", "Bruno",
                                "cpf", numeric(11),
                                "email", uniqueEmail("bruno"),
                                "telefone", numeric(11),
                                "endereco", "Rua Y, 456"
                        ))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        String alunoId = mapper.readTree(alunoRes).get("id").asText();

        // 2) cria disciplina com código único
        var discRes = mvc.perform(post("/disciplinas")
                        .with(jwtProfessor())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of(
                                "codigo", code(),
                                "nome", "POO I"
                        ))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        String discId = mapper.readTree(discRes).get("id").asText();

        // 3) matrícula
        var matRes = mvc.perform(post("/matriculas")
                        .with(jwtProfessor())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of(
                                "alunoId", alunoId,
                                "disciplinaId", discId
                        ))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        JsonNode matJson = mapper.readTree(matRes);
        assertThat(matJson.get("id").asText()).isNotBlank();

        // 4) atribui nota 8.5 (aprovado)
        mvc.perform(put("/matriculas/nota")
                        .with(jwtProfessor())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of(
                                "alunoId", alunoId,
                                "disciplinaId", discId,
                                "nota", 8.5
                        ))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nota").value(8.5));

        // 5) lista aprovados
        var aprovados = mvc.perform(get("/matriculas/aprovados/{disc}", discId)
                        .with(jwtProfessor()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var aprovadosArr = mapper.readTree(aprovados);
        assertThat(aprovadosArr.get(0).get("alunoId").asText()).isEqualTo(alunoId);
        assertThat(aprovadosArr.get(0).get("alunoNome").asText()).isNotBlank();

        // 6) rebaixa nota para 6.0 (reprovado)
        mvc.perform(put("/matriculas/nota")
                        .with(jwtProfessor())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of(
                                "alunoId", alunoId,
                                "disciplinaId", discId,
                                "nota", 6.0
                        ))))
                .andDo(print())
                .andExpect(status().isOk());

        var reprovados = mvc.perform(get("/matriculas/reprovados/{disc}", discId)
                        .with(jwtProfessor()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var reprovadosArr = mapper.readTree(reprovados);
        assertThat(reprovadosArr.get(0).get("alunoId").asText()).isEqualTo(alunoId);
        assertThat(reprovadosArr.get(0).get("alunoNome").asText()).isNotBlank();

        // 7) lista por disciplina (com alunoNome)
        var porDisciplina = mvc.perform(get("/matriculas/disciplina/{disc}", discId)
                        .with(jwtProfessor()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var list = mapper.readTree(porDisciplina);
        assertThat(list.get(0).get("alunoId").asText()).isEqualTo(alunoId);
        assertThat(list.get(0).get("alunoNome").asText()).isNotBlank();
    }
}