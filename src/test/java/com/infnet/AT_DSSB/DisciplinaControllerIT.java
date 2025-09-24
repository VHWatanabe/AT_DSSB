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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DisciplinaControllerIT extends CleanMongoPerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    private static final Map<String, Object> JWT_CLAIMS =
            Map.of("resource_access", Map.of("at-dssb-api", Map.of("roles", List.of("PROFESSOR"))));

    private static RequestPostProcessor jwtProfessor() {
        return jwt()
                .jwt(j -> j.claims(c -> c.putAll(JWT_CLAIMS)))
                .authorities(new SimpleGrantedAuthority("ROLE_PROFESSOR"));
    }

    private static String unique(String p) {
        return p + "-" + UUID.randomUUID().toString().substring(0,8);
    }

    @Test
    void criaEListaDisciplina_comJwtProfessor_ok() throws Exception {
        var body = Map.of("codigo", unique("MAT101"), "nome", "Cálculo I");

        mvc.perform(post("/disciplinas")
                        .with(jwtProfessor())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.codigo").value(body.get("codigo")));

        mvc.perform(get("/disciplinas").with(jwtProfessor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Cálculo I"));
    }
}