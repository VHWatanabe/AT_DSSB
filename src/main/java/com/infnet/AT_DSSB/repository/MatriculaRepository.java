package com.infnet.AT_DSSB.repository;

import com.infnet.AT_DSSB.model.Matricula;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MatriculaRepository extends MongoRepository<Matricula, String> {
    Optional<Matricula> findByAlunoIdAndDisciplinaId(String alunoId, String disciplinaId);

    List<Matricula> findByDisciplinaIdAndNotaGreaterThanEqual(String disciplinaId, Double notaCorte);
    List<Matricula> findByDisciplinaIdAndNotaLessThan(String disciplinaId, Double notaCorte);
    List<Matricula> findByDisciplinaId(String disciplinaId);
}