package com.infnet.AT_DSSB.unit.config;

import com.infnet.AT_DSSB.service.AlunoService;
import com.infnet.AT_DSSB.service.DisciplinaService;
import com.infnet.AT_DSSB.service.MatriculaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SecurityConfigExtraTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private AlunoService alunoService;
    @MockBean private DisciplinaService disciplinaService;
    @MockBean private MatriculaService matriculaService;

    @Test
    void devePermitirAcessoAoHealth() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
    }

    @Test
    void deveNegarAcessoAoInfo_semAuth() throws Exception {
        mockMvc.perform(get("/actuator/info"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deveNegarAcessoAoInfo_comJwtSemRole() throws Exception {
        mockMvc.perform(get("/actuator/info").with(jwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    void devePermitirAcessoAoInfo_comRoleProfessor() throws Exception {
        mockMvc.perform(get("/actuator/info")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_PROFESSOR"))))
                .andExpect(status().isOk());
    }
}