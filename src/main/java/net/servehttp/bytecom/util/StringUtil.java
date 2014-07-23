package net.servehttp.bytecom.util;

import java.util.Date;
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

	public char getChar(String line, int index){
		return line.charAt(index);
	}

	public int getInt(String line, int inicio, int fim){
		return Integer.parseInt(get(line, inicio, fim));
	}

	public double getDouble2Decimal(String line, int inicio, int fim){
		return Double.parseDouble(get(line, inicio, fim)) / 100;
	}

	public Date getDataHora(String line, int inicio, int fim){
		String data = get(line, inicio, fim);
		return DateUtil.INSTANCE.parse(data, "ddMMyyyyHHmmss");
	}

	public Date getData(String line, int inicio, int fim){
		String data = get(line, inicio, fim);
		return DateUtil.INSTANCE.parse(data, "ddMMyyyy");
	}

	public String formatCurrence(double value) {
		Locale brasil = new Locale("pt", "BR");
		return String.format(brasil, "%1$,.2f", value);
	}

	public String valorPorExtenso(double valor) {
		extenso.setNumber(valor);
		return extenso.toString();
	}
}
