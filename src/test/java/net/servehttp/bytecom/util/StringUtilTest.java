package net.servehttp.bytecom.util;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {

	@Test
	public void deveriaSerUmCpfInvalidoSemMascara() {
		String cpf = "93848116750";
		Assert.assertFalse(StringUtil.INSTANCE.isCpf(cpf));
	}

	@Test
	public void deveriaSerUmCpfInvalidoComMascara() {
		String cpf = "938.481.167.50";
		Assert.assertFalse(StringUtil.INSTANCE.isCpf(cpf));
	}

	@Test
	public void deveriaSerUmCpfValidoPassandoSemMascara() {
		String cpf = "75504898315";
		Assert.assertTrue(StringUtil.INSTANCE.isCpf(cpf));
	}

	@Test
	public void deveriaSerUmCpfValidoPassandoComMascara() {
		String cpf = "755.048.983.15";
		Assert.assertTrue(StringUtil.INSTANCE.isCpf(cpf));
	}
	
	@Test
	public void deveriaSerUmCnpjValidoPassandoComMascara() {
		String cnpj = "11.558.756/0001-77";
		Assert.assertTrue(StringUtil.INSTANCE.isCnpj(cnpj));
	}
	
	@Test
	public void deveriaSerUmCnpjValidoPassandoSemMascara() {
		String cnpj = "11558756000177";
		Assert.assertTrue(StringUtil.INSTANCE.isCnpj(cnpj));
	}

	@Test
	public void deveriaSerUmCnpjInvalidoPassandoComMascara() {
		String cnpj = "32.840.028/8503-85";
		Assert.assertFalse(StringUtil.INSTANCE.isCnpj(cnpj));
	}
	
	@Test
	public void deveriaSerUmCnpjInvalidoPassandoSemMascara() {
		String cnpj = "32840028850385";
		Assert.assertFalse(StringUtil.INSTANCE.isCnpj(cnpj));
	}

	@Test
	public void deveriaGerarOValorExtensoDeUmNumeroDeCentavos() {
		double valor = 0.69;
		String valorPorExtensoEsperado = "sessenta e nove centavos";
		String valorPorExtensoAtual = StringUtil.INSTANCE.valorPorExtenso(valor);
		Assert.assertEquals(valorPorExtensoEsperado, valorPorExtensoAtual);
	}

	@Test
	public void deveriaGerarOValorExtensoDeUmNumeroUnidade() {
		double valor = 1.69;
		String valorPorExtensoEsperado = "hum real e sessenta e nove centavos";
		String valorPorExtensoAtual = StringUtil.INSTANCE.valorPorExtenso(valor);
		Assert.assertEquals(valorPorExtensoEsperado, valorPorExtensoAtual);
	}

	@Test
	public void deveriaGerarOValorExtensoDeUmNumeroDezenas() {
		double valor = 41.69;
		String valorPorExtensoEsperado = "quarenta e um reais e sessenta e nove centavos";
		String valorPorExtensoAtual = StringUtil.INSTANCE.valorPorExtenso(valor);
		Assert.assertEquals(valorPorExtensoEsperado, valorPorExtensoAtual);
	}

	@Test
	public void deveriaGerarOValorExtensoDeUmNumeroCentenas() {
		double valor = 341.69;
		String valorPorExtensoEsperado = "trezentos e quarenta e um reais e sessenta e nove centavos";
		String valorPorExtensoAtual = StringUtil.INSTANCE.valorPorExtenso(valor);
		Assert.assertEquals(valorPorExtensoEsperado, valorPorExtensoAtual);
	}

	@Test
	public void deveriaGerarOValorExtensoDeUmNumeroMilhar() {
		double valor = 35321.69;
		String valorPorExtensoEsperado = "trinta e cinco mil e trezentos e vinte e um reais e sessenta e nove centavos";
		String valorPorExtensoAtual = StringUtil.INSTANCE.valorPorExtenso(valor);
		Assert.assertEquals(valorPorExtensoEsperado, valorPorExtensoAtual);
	}

	@Test
	public void deveriaGerarOValorExtensoDeUmNumeroMilhao() {
		double valor = 1235321.69;
		String valorPorExtensoEsperado = "hum milhão e duzentos e trinta e cinco mil e trezentos e vinte e um reais e sessenta e nove centavos";
		String valorPorExtensoAtual = StringUtil.INSTANCE.valorPorExtenso(valor);
		Assert.assertEquals(valorPorExtensoEsperado, valorPorExtensoAtual);
	}

	@Test
	public void deveriaGerarOValorExtensoDeUmNumeroMilhoes() {
		double valor = 4235321.69;
		String valorPorExtensoEsperado = "quatro milhões e duzentos e trinta e cinco mil e trezentos e vinte e um reais e sessenta e nove centavos";
		String valorPorExtensoAtual = StringUtil.INSTANCE.valorPorExtenso(valor);
		Assert.assertEquals(valorPorExtensoEsperado, valorPorExtensoAtual);
	}

}
