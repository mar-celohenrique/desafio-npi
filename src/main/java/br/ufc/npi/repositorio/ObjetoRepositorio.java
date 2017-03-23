package br.ufc.npi.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufc.npi.bean.Objeto;
import br.ufc.npi.bean.Usuario;
import java.util.List;
import br.ufc.npi.bean.Amigo;

public interface ObjetoRepositorio extends JpaRepository<Objeto, Integer> {
	List<Objeto> findByUsuario(Usuario usuario);

	List<Objeto> findByAmigo(Amigo amigo);

	@Query("from Objeto o where o.emprestado = false")
	List<Objeto> objetosDisponiveis();
	
	@Query("from Objeto o where o.emprestado = true")
	List<Objeto> objetosEmprestados();
}
