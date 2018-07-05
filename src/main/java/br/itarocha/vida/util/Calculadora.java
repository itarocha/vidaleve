package br.itarocha.vida.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

public class Calculadora {

	public static Integer getIdade(Date data) {
		Calendar dataNascimento = Calendar.getInstance();
		dataNascimento.setTime(data);
		Calendar dataAtual = Calendar.getInstance();
		Integer diferencaMes = dataAtual.get(Calendar.MONTH) - dataNascimento.get(Calendar.MONTH);
		Integer diferencaDia = dataAtual.get(Calendar.DAY_OF_MONTH) - dataNascimento.get(Calendar.DAY_OF_MONTH);
		Integer idade = (dataAtual.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR));
		if(diferencaMes < 0	|| (diferencaMes == 0 && diferencaDia < 0)) {
			idade--;
		}
		return idade;
	}
	
	// Cálculo do índice de massa corporal = peso (quilos) ÷ altura² (metros) 
	public static BigDecimal imc(BigDecimal peso, BigDecimal altura) {
		BigDecimal quadrado = altura.pow(2);
		return (BigDecimal.ZERO.equals(quadrado)) ? BigDecimal.ZERO : peso.divide(quadrado,2,RoundingMode.UP);  
	}
}
