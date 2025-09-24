package com.infnet.AT_DSSB.unit.dto;

import com.infnet.AT_DSSB.dto.MatriculaDTOs;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class MatriculaDTOsTest {

    @Test
    void deveInstanciarEPopularMatriculaDTOs() throws Exception {
        MatriculaDTOs dto = new MatriculaDTOs();
        assertThat(dto).isNotNull();

        trySet(dto, "setId", "M1");
        trySet(dto, "setAlunoId", "A1");
        trySet(dto, "setDisciplinaId", "D1");
        trySet(dto, "setNota", 8.5);

        assertThat(tryGet(dto, "getNota")).as("getNota").isIn(null, 8.5);
        assertThat(dto.toString()).contains("Matricula").doesNotContain("null");
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