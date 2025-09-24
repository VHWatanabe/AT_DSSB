package com.infnet.AT_DSSB;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AtDssbApplicationMainTest {

    @Test
    void deveExecutarMainSemErros() {
        AtDssbApplication.main(new String[]{});
        assertThat(true).isTrue();
    }
}