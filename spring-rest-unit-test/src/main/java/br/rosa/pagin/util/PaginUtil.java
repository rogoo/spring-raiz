package br.rosa.pagin.util;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import br.rosa.pagin.util.constantes.PaginConstantes;

public class PaginUtil {

	public static final String PADRAO_DATA_DDMMYYYY = "dd/MM/yyyy";

	private PaginUtil() {
		super();
	}

	/**
	 * Valida objeto inválido.
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> boolean validado(T objeto) {
		if (objeto instanceof String) {
			return objeto != null && !PaginConstantes.CARAC_VAZIO.equals(((String) objeto).trim());
		} else {
			return objeto != null;
		}
	}

	/**
	 * Formata data no padrão dd/mm/yyyy.
	 * 
	 * @param data
	 * @return
	 */
	public static String formataDataPadraoDDMMYYYY(Date data) {
		if (data == null) {
			return null;
		}

		return DateFormatUtils.format(data, PADRAO_DATA_DDMMYYYY);
	}
}
