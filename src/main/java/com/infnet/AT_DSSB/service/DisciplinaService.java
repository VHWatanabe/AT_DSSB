package com.infnet.AT_DSSB.service;

import com.infnet.AT_DSSB.dto.DisciplinaDTOs;
import com.infnet.AT_DSSB.exception.NotFoundException;
import com.infnet.AT_DSSB.model.Disciplina;
import com.infnet.AT_DSSB.repository.DisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DisciplinaService {

    private final DisciplinaRepository repo;

    public DisciplinaDTOs.Response criar(DisciplinaDTOs.Create in) {
        repo.findByCodigo(in.getCodigo())
                .ifPresent(d -> { throw new IllegalArgumentException("Código de disciplina já existe"); });

        var salvo = repo.save(Disciplina.builder()
                .codigo(in.getCodigo())
                .nome(in.getNome())
                .build());

        return toResponse(salvo);
    }

    public List<DisciplinaDTOs.Response> listarTodas() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public Disciplina getOrThrow(String id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Disciplina não encontrada: " + id));
    }

    private DisciplinaDTOs.Response toResponse(Disciplina d) {
        return DisciplinaDTOs.Response.builder()
                .id(d.getId())
                .codigo(d.getCodigo())
                .nome(d.getNome())
                .build();
    }
}