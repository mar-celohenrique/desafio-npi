package br.ufc.npi.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.npi.bean.Amigo;

public interface AmigoRepositorio extends JpaRepository<Amigo, Integer>{

}
