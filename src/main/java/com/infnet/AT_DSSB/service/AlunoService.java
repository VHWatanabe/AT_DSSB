package com.infnet.AT_DSSB.service;

import com.infnet.AT_DSSB.dto.AlunoDTOs;
import com.infnet.AT_DSSB.exception.NotFoundException;
import com.infnet.AT_DSSB.model.Aluno;
import com.infnet.AT_DSSB.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository repo;

    public AlunoDTOs.Response criar(AlunoDTOs.Create in) {
        repo.findByCpf(in.getCpf()).ifPresent(a -> { throw new IllegalArgumentException("CPF já cadastrado"); });
        repo.findByEmail(in.getEmail()).ifPresent(a -> { throw new IllegalArgumentException("E-mail já cadastrado"); });

        var salvo = repo.save(Aluno.builder()
                .nome(in.getNome())
                .cpf(in.getCpf())
                .email(in.getEmail())
                .telefone(in.getTelefone())
                .endereco(in.getEndereco())
                .build());

        return toResponse(salvo);
    }

    public List<AlunoDTOs.Response> listarTodos() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public Aluno getOrThrow(String id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Aluno não encontrado: " + id));
    }

    private AlunoDTOs.Response toResponse(Aluno a) {
        return AlunoDTOs.Response.builder()
                .id(a.getId())
                .nome(a.getNome())
                .cpf(a.getCpf())
                .email(a.getEmail())
                .telefone(a.getTelefone())
                .endereco(a.getEndereco())
                .build();
    }
}