package com.infnet.AT_DSSB;

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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AlunoControllerIT extends CleanMongoPerTest {

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
        var base = String.valueOf(System.nanoTime()) + String.valueOf(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        var digits = base.replaceAll("\\D", "");
        if (digits.length() < len) {
            digits = digits + "00000000000";
        }
        return digits.substring(0, len);
    }

    private static String uniqueEmail(String prefix) {
        return prefix + "+" + UUID.randomUUID().toString().substring(0, 8) + "@ex.com";
    }

    @Test
    void criaEListaAluno_comJwtProfessor_ok() throws Exception {
        var body = Map.of(
                "nome", "Ana",
                "cpf", numeric(11),
                "email", uniqueEmail("ana"),
                "telefone", numeric(11),
                "endereco", "Rua X, 123"
        );

        mvc.perform(post("/alunos")
                        .with(jwtProfessor())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(body)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.cpf").value(body.get("cpf")));

        mvc.perform(get("/alunos").with(jwtProfessor()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").exists());
    }
}