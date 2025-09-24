package com.infnet.AT_DSSB.unit.exception;

import com.infnet.AT_DSSB.exception.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotFoundExceptionTest {

    @Test
    void deveConterMensagem() {
        NotFoundException ex = new NotFoundException("Recurso não encontrado");
        assertThat(ex).isNotNull();
        assertThat(ex.getMessage()).isEqualTo("Recurso não encontrado");
    }
}
