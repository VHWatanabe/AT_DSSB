package com.infnet.AT_DSSB.controller;

import com.infnet.AT_DSSB.dto.DisciplinaDTOs;
import com.infnet.AT_DSSB.service.DisciplinaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
@RequiredArgsConstructor
public class DisciplinaController {

    private final DisciplinaService service;

    @PostMapping
    public ResponseEntity<DisciplinaDTOs.Response> criar(@Valid @RequestBody DisciplinaDTOs.Create body) {
        var out = service.criar(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(out);
    }

    @GetMapping
    public List<DisciplinaDTOs.Response> listar() {
        return service.listarTodas();
    }
}
