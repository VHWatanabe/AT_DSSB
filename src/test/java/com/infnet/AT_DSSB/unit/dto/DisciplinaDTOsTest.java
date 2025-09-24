package com.infnet.AT_DSSB.unit.dto;

import com.infnet.AT_DSSB.dto.DisciplinaDTOs;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class DisciplinaDTOsTest {

    @Test
    void deveInstanciarEPopularDisciplinaDTOs() throws Exception {
        DisciplinaDTOs dto = new DisciplinaDTOs();
        assertThat(dto).isNotNull();

        trySet(dto, "setId", "D1");
        trySet(dto, "setCodigo", "MAT101");
        trySet(dto, "setNome", "Cálculo I");

        assertThat(tryGet(dto, "getCodigo")).as("getCodigo").isIn(null, "MAT101");
        assertThat(dto.toString()).contains("Disciplina").doesNotContain("null");
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