package br.itarocha.vida.util;

import java.math.BigDecimal;
import java.util.UUID;

public class TesteMain {
	
	public static void main(String... args) {
		BigDecimal peso = new BigDecimal(87);
		BigDecimal altura = new BigDecimal(1.74);
		BigDecimal resultado = Calculadora.imc(peso, altura);
		

		
		System.out.println(resultado.floatValue());

		for (int i = 0; i < 10; i++) {
			UUID uuid = UUID.randomUUID();
			String strUuid = uuid.toString();
			System.out.println(strUuid);
		}
	}

}
