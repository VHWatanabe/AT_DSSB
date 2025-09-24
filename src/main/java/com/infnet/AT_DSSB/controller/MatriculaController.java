package com.infnet.AT_DSSB.controller;

import com.infnet.AT_DSSB.dto.MatriculaDTOs;
import com.infnet.AT_DSSB.service.MatriculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaService service;

    @PostMapping
    public MatriculaDTOs.Response matricular(@Valid @RequestBody MatriculaDTOs.Enroll body) {
        return service.matricular(body);
    }

    @PutMapping("/nota")
    public MatriculaDTOs.Response atribuirNota(@Valid @RequestBody MatriculaDTOs.SetNota body) {
        return service.atribuirNota(body);
    }

    @GetMapping("/aprovados/{disciplinaId}")
    public List<MatriculaDTOs.Resultado> aprovados(@PathVariable String disciplinaId) {
        return service.listarAprovados(disciplinaId);
    }

    @GetMapping("/reprovados/{disciplinaId}")
    public List<MatriculaDTOs.Resultado> reprovados(@PathVariable String disciplinaId) {
        return service.listarReprovados(disciplinaId);
    }

    @GetMapping("/disciplina/{disciplinaId}")
    public List<MatriculaDTOs.Response> porDisciplina(@PathVariable String disciplinaId) {
        return service.listarPorDisciplina(disciplinaId);
    }
}