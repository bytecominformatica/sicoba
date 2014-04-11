package net.servehttp.bytecom.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public enum DateUtil {
	;

	private final static SimpleDateFormat sdfData = new SimpleDateFormat(
			"dd/MM/yyyy");

	/**
	 * 
	 * Converte uma data para String no formato <b>dd/MM/yyyy<b>.
	 * 
	 * <pre>
	 * @param dataInstalacao Date
	 * @return String
	 * </pre>
	 */
	public static String format(Date date) {
		return sdfData.format(date);
	}

	/**
	 * 
	 * Converte uma String no formato <b>dd/MM/yyyy<b> para java.util.Date.
	 * 
	 * <pre>
	 * @param dataInstalacao Date
	 * @return java.util.Date
	 * </pre>
	 */
	public static Date parse(String dateString) {
		Date date = null;
		try {
			date = sdfData.parse(dateString);
		} catch (ParseException e) {
		}
		return date;
	}

	/**
	 * Retorna um Date apenas com informações de data, zerando informações de
	 * tempo.
	 * 
	 * @return java.util.Date
	 */
	public static Date getHoje() {
		String hojeString = format(new Date());
		Date hoje = parse(hojeString);
		return hoje;
	}

	/**
	 * Retorna a data atual adicionado mais 1 mês.
	 * 
	 * @return Calendar
	 */
	public static Calendar getProximoMes() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, 1);
		return c;
	}

	public static Date getDate(int dia, int mes, int ano) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(ano, mes, dia);
		return c.getTime();
	}

	public static Date getUltimoDiaDoMes(Date data) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 1);
		return c.getTime();
	}

	/**
	 * Incrementa o mês da data atual. O mês pode ser incrementado para mais ou
	 * para menos de acordo com o sinal da quantidade.
	 * 
	 * <pre>
	 * @param quantidade int
	 * @return java.util.Date
	 * </pre>
	 */
	public static Date incrementaMesAtual(int quantidade) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, quantidade);
		return c.getTime();
	}

}
