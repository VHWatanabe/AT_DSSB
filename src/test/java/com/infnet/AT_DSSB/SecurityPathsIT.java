package com.infnet.AT_DSSB;

import com.infnet.AT_DSSB.support.CleanMongoPerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityPathsIT extends CleanMongoPerTest {

    @Autowired MockMvc mvc;

    @Test
    void semJwt_401() throws Exception {
        mvc.perform(get("/alunos")).andExpect(status().isUnauthorized());
    }

    @Test
    void comJwtSemRole_403() throws Exception {
        mvc.perform(get("/alunos").with(jwt()))
                .andExpect(status().isForbidden());
    }
}
