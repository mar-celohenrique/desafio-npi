package br.ufc.npi.criptografia;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class Criptografia {
	
	public String criptografarSenha(String senha) {
		return new Base64().encodeToString(senha.getBytes());
	}
}
