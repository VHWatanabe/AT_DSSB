package com.infnet.AT_DSSB.controller;

import com.infnet.AT_DSSB.dto.AlunoDTOs;
import com.infnet.AT_DSSB.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService service;

    @PostMapping
    public ResponseEntity<AlunoDTOs.Response> criar(@Valid @RequestBody AlunoDTOs.Create body) {
        var out = service.criar(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(out);
    }

    @GetMapping
    public List<AlunoDTOs.Response> listar() {
        return service.listarTodos();
    }
}
