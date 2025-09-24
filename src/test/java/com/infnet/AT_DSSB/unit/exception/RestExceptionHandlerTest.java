package com.infnet.AT_DSSB.unit.exception;

import com.infnet.AT_DSSB.exception.RestExceptionHandler;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RestExceptionHandlerTest {

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(new BoomController())
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @RestController
    static class BoomController {

        @GetMapping("/boom/illegal")
        public void illegal() {
            throw new IllegalArgumentException("cpf inválido");
        }

        static class NovoAlunoDTO {
            @NotBlank(message = "nome é obrigatório")
            public String nome;
        }

        @PostMapping(value = "/boom/valid", consumes = MediaType.APPLICATION_JSON_VALUE)
        public void valida(@RequestBody @Valid NovoAlunoDTO body) {
        }
    }

    @Test
    void deveTratarIllegalArgumentException_com422EBody() throws Exception {
        mvc.perform(get("/boom/illegal"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value(containsString("cpf inválido")));
    }

    @Test
    void deveTratarValidacao_com400EBodyDeErros() throws Exception {
        String payloadInvalido = "{}";

        mvc.perform(post("/boom/valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasKey("nome")))
                .andExpect(jsonPath("$.nome").value("nome é obrigatório"));
    }
}