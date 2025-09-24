package com.infnet.AT_DSSB.repository;

import com.infnet.AT_DSSB.model.Disciplina;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DisciplinaRepository extends MongoRepository<Disciplina, String> {
    Optional<Disciplina> findByCodigo(String codigo);
}
