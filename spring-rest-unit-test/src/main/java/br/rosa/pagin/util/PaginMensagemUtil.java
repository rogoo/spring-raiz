package br.rosa.pagin.util;

import java.text.MessageFormat;

import br.rosa.pagin.util.constantes.PaginMensagensConstantes;

public class PaginMensagemUtil {

	private PaginMensagemUtil() {
		super();
	}

	/**
	 * Cria mensagem negocial de campo obrigatório.
	 * 
	 * @param parametros
	 * @return
	 */
	public static String criarMensagemCampoObrigatorio(Object... parametros) {
		return criarMensagem(PaginMensagensConstantes.MSG_CAMPO_OBRIGATORIO, parametros);
	}

	/**
	 * Cria mensagem negocial.
	 * 
	 * @param mensagem
	 * @param parametros
	 * @return
	 */
	public static String criarMensagem(String mensagem, Object... parametros) {
		return MessageFormat.format(mensagem, parametros);
	}
}
