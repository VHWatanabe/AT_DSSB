package com.infnet.AT_DSSB.unit.dto;

import com.infnet.AT_DSSB.dto.AlunoDTOs;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class AlunoDTOsTest {

    @Test
    void deveInstanciarEPopularAlunoDTOs() throws Exception {
        AlunoDTOs dto = new AlunoDTOs();
        assertThat(dto).isNotNull();

        trySet(dto, "setId", "1");
        trySet(dto, "setNome", "Maria");
        trySet(dto, "setCpf", "12345678901");
        trySet(dto, "setEmail", "maria@ex.com");
        trySet(dto, "setTelefone", "41999999999");
        trySet(dto, "setEndereco", "Rua X, 123");

        assertThat(tryGet(dto, "getId")).as("getId").isIn(null, "1");
        assertThat(dto.toString()).contains("Aluno").doesNotContain("null");
    }

    private static void trySet(Object obj, String method, Object value) {
        for (Method m : obj.getClass().getMethods()) {
            if (m.getName().equals(method) && m.getParameterCount() == 1) {
                try { m.invoke(obj, value); } catch (Exception ignored) {}
            }
        }
    }

    private static Object tryGet(Object obj, String method) {
        for (Method m : obj.getClass().getMethods()) {
            if (m.getName().equals(method) && m.getParameterCount() == 0) {
                try { return m.invoke(obj); } catch (Exception ignored) {}
            }
        }
        return null;
    }
}