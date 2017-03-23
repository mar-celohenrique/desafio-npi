package br.ufc.npi.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.npi.bean.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
	Usuario findById(int id);

	Usuario findByLogin(String login);

	Usuario findByEmail(String email);

	Usuario findByEmailAndSenha(String email, String senha);
}
