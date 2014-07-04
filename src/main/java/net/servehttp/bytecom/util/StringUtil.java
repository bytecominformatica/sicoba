package net.servehttp.bytecom.util;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;

/**
 * 
 * @author clairton
 */
public enum StringUtil {
	INSTANCE;
	
	private Extenso extenso = new Extenso();
	
	public String get(String line, int inicio, int fim){
		return line.substring(inicio, fim);
	}

	public int getInt(String line, int inicio, int fim){
		return Integer.parseInt(get(line, inicio, fim));
	}

	public double getDouble(String line, int inicio, int fim){
		return Double.parseDouble(get(line, inicio, fim));
	}

	public Date getData(String line, int inicio, int fim){
		String data = get(line, inicio, fim);
		return DateUtil.INSTANCE.parse(data, "ddMMyyyyHHmmss");
	}

	public String formatCurrence(double value) {
		Locale brasil = new Locale("pt", "BR");
		return String.format(brasil, "%1$,.2f", value);
	}

	public String valorPorExtenso(double valor) {
		extenso.setNumber(valor);
		return extenso.toString();
	}

	/**
	 * Realiza a validação do CPF.
	 * 
	 * @param cpf
	 *            número de CPF a ser validado pode ser passado no formado
	 *            999.999.999-99 ou 99999999999
	 * @return true se o CPF é válido e false se não é válido
	 */
	public boolean isCpf(String cpf) {
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");

		int d1, d2;
		int digito1, digito2, resto;
		int digitoCPF;
		String nDigResult;

		d1 = d2 = 0;
		digito1 = digito2 = resto = 0;

		for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
			digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();

			// multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4
			// e assim por diante.
			d1 = d1 + (11 - nCount) * digitoCPF;

			// para o segundo digito repita o procedimento incluindo o primeiro
			// digito calculado no passo anterior.
			d2 = d2 + (12 - nCount) * digitoCPF;
		}
		;

		// Primeiro resto da divisão por 11.
		resto = (d1 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
		// menos o resultado anterior.
		if (resto < 2)
			digito1 = 0;
		else
			digito1 = 11 - resto;

		d2 += 2 * digito1;

		// Segundo resto da divisão por 11.
		resto = (d2 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
		// menos o resultado anterior.
		if (resto < 2)
			digito2 = 0;
		else
			digito2 = 11 - resto;

		// Digito verificador do CPF que está sendo validado.
		String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());

		// Concatenando o primeiro resto com o segundo.
		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

		// comparar o digito verificador do cpf com o primeiro resto + o segundo
		// resto.
		return nDigVerific.equals(nDigResult);
	}

	/**
	 * Realiza a validação de um cnpj
	 * 
	 * @param cnpj
	 *            String - o CNPJ pode ser passado no formato 99.999.999/9999-99
	 *            ou 99999999999999
	 * @return boolean
	 */
	public boolean isCnpj(String cnpj) {
		cnpj = cnpj.replace(".", "");
		cnpj = cnpj.replace("-", "");
		cnpj = cnpj.replace("/", "");
		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
		if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") || cnpj.equals("22222222222222")
				|| cnpj.equals("33333333333333") || cnpj.equals("44444444444444") || cnpj.equals("55555555555555")
				|| cnpj.equals("66666666666666") || cnpj.equals("77777777777777") || cnpj.equals("88888888888888")
				|| cnpj.equals("99999999999999") || (cnpj.length() != 14))
			return (false);
		char dig13, dig14;
		int sm, i, r, num, peso; // "try" - protege o código para eventuais
									// erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {
				// converte o i-ésimo caractere do CNPJ em um número: // por
				// exemplo, transforma o caractere '0' no inteiro 0 // (48 eh a
				// posição de '0' na tabela ASCII)
				num = (int) (cnpj.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (cnpj.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}
			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);
			// Verifica se os dígitos calculados conferem com os dígitos
			// informados.
			if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}
}
