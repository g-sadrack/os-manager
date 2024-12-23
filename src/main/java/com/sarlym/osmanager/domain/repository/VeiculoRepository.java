package com.sarlym.osmanager.domain.repository;

import com.sarlym.osmanager.domain.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    @Query("SELECT v FROM Veiculo v WHERE LOWER(v.placa) = LOWER(:placa)")
    Optional<Veiculo> findVeiculoByPlacaIgnoreCase(@Param("placa") String placa);

    Optional<Veiculo> findVeiculoByPlaca(String placa);

}
