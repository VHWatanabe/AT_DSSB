package com.infnet.AT_DSSB.service;

import com.infnet.AT_DSSB.dto.MatriculaDTOs;
import com.infnet.AT_DSSB.exception.NotFoundException;
import com.infnet.AT_DSSB.model.Matricula;
import com.infnet.AT_DSSB.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaService {

    private final MatriculaRepository repo;
    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;

    public MatriculaDTOs.Response matricular(MatriculaDTOs.Enroll in) {
        var aluno = alunoService.getOrThrow(in.getAlunoId());
        var disciplina = disciplinaService.getOrThrow(in.getDisciplinaId());

        repo.findByAlunoIdAndDisciplinaId(aluno.getId(), disciplina.getId())
                .ifPresent(m -> { throw new IllegalArgumentException("Aluno já matriculado nesta disciplina"); });

        var salvo = repo.save(Matricula.builder()
                .alunoId(aluno.getId())
                .disciplinaId(disciplina.getId())
                .nota(null)
                .build());

        return toResponseWithAluno(salvo);
    }

    public MatriculaDTOs.Response atribuirNota(MatriculaDTOs.SetNota in) {
        alunoService.getOrThrow(in.getAlunoId());
        disciplinaService.getOrThrow(in.getDisciplinaId());

        var mat = repo.findByAlunoIdAndDisciplinaId(in.getAlunoId(), in.getDisciplinaId())
                .orElseThrow(() -> new NotFoundException("Matrícula não encontrada para aluno e disciplina informados"));

        mat.setNota(in.getNota());
        var salvo = repo.save(mat);

        return toResponseWithAluno(salvo);
    }

    public List<MatriculaDTOs.Resultado> listarAprovados(String disciplinaId) {
        disciplinaService.getOrThrow(disciplinaId);
        return repo.findByDisciplinaIdAndNotaGreaterThanEqual(disciplinaId, 7.0)
                .stream()
                .map(this::toResultado)
                .toList();
    }

    public List<MatriculaDTOs.Resultado> listarReprovados(String disciplinaId) {
        disciplinaService.getOrThrow(disciplinaId);
        return repo.findByDisciplinaIdAndNotaLessThan(disciplinaId, 7.0)
                .stream()
                .map(this::toResultado)
                .toList();
    }

    public List<MatriculaDTOs.Response> listarPorDisciplina(String disciplinaId) {
        disciplinaService.getOrThrow(disciplinaId);
        return repo.findByDisciplinaId(disciplinaId)
                .stream()
                .map(this::toResponseWithAluno)
                .toList();
    }

    private MatriculaDTOs.Resultado toResultado(Matricula m) {
        var aluno = alunoService.getOrThrow(m.getAlunoId());
        return MatriculaDTOs.Resultado.builder()
                .alunoId(aluno.getId())
                .alunoNome(aluno.getNome())
                .nota(m.getNota())
                .build();
    }

    private MatriculaDTOs.Response toResponseWithAluno(Matricula m) {
        var aluno = alunoService.getOrThrow(m.getAlunoId());
        return MatriculaDTOs.Response.builder()
                .id(m.getId())
                .alunoId(m.getAlunoId())
                .alunoNome(aluno.getNome())
                .disciplinaId(m.getDisciplinaId())
                .nota(m.getNota())
                .build();
    }
}