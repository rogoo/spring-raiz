package br.rosa.pagin.util.constantes;

public class PaginMensagensConstantes {

	private PaginMensagensConstantes() {
		super();
	}

	public static final String MSG_CAMPO_OBRIGATORIO = "O campo {0} é de preenchimento obrigatório.";
	public static final String MSG_OCORREU_ERRO = "Ocorreu erro.";
	public static final String MSG_NAO_EXISTE_COM_IDENTIFICADOR = "Não existe o {0} com identificador {1}";
	public static final String MSG_SEM_ALTERACAO = "Não foi detectado alterações, portanto sem atualização.";
	public static final String MSG_PREENCHA_TODOS_CAMPOS = "Não foi informado todos os campos.";
	public static final String MSG_LIMITE_RETORNO_REGISTROS = "O tamanho máximo da quantidade de registros retornados é 100";
	public static final String MSG_DELECAO_EXISTE_VINCULACAO = "O {0} (id: {1}) possui vinculação com {2} e portanto não é possível realizar a exclusão.";
}
