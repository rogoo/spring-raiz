package br.rosa.pagin.dto.response;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.rosa.pagin.mapper.ErroDTOMapper;

@JsonInclude(Include.NON_NULL)
public class RespostaDTO<T> extends RespostaAbstractDTO<T> {

	private static final int HTTP_CODE_SUCCESS = 200;
	private static final int HTTP_CODE_SUCCESS_CREATED = 201;
	private static final int HTTP_CODE_BAD_REQUEST = 400;
	private static final int HTTP_CODE_BAD_REQUEST_NOT_FOUND = 404;
	private int codigo;
	private T retorno;
	private List<ErroDTO> listaErro;
	private int totalRegistros;

	public static <T> RespostaDTO<T> criarRespostaSucesso(T retorno) {
		RespostaDTO<T> resposta = new RespostaDTO<T>();

		resposta.setCodigo(HTTP_CODE_SUCCESS);
		resposta.setRetorno(retorno);

		return resposta;
	}

	public static <T> RespostaDTO<T> criarRespostaSucesso(T retorno, int totalRegistros) {
		RespostaDTO<T> resposta = new RespostaDTO<T>();

		resposta.setCodigo(HTTP_CODE_SUCCESS);
		resposta.setRetorno(retorno);
		resposta.setTotalRegistros(totalRegistros);

		return resposta;
	}

	public static <T> RespostaDTO<T> criarRespostaSucesso() {
		RespostaDTO<T> resposta = new RespostaDTO<T>();

		resposta.setCodigo(HTTP_CODE_SUCCESS);

		return resposta;
	}

	public static <T> RespostaDTO<T> criarRespostaSucessoCreated() {
		RespostaDTO<T> resposta = new RespostaDTO<T>();

		resposta.setCodigo(HTTP_CODE_SUCCESS_CREATED);

		return resposta;
	}

	public static <T> RespostaDTO<T> criarRespostaErro(List<String> listaErro) {
		RespostaDTO<T> resposta = new RespostaDTO<T>();

		resposta.setCodigo(HTTP_CODE_BAD_REQUEST);
		resposta.setListaErro(ErroDTOMapper.converte(listaErro));

		return resposta;
	}

	public static <T> RespostaDTO<T> criarRespostaErro(String erro) {
		RespostaDTO<T> resposta = new RespostaDTO<T>();

		resposta.setCodigo(HTTP_CODE_BAD_REQUEST);
		resposta.setListaErro(Arrays.asList(new ErroDTO(erro)));

		return resposta;
	}

	public static <T> RespostaDTO<T> criarRespostaErroNotFound() {
		RespostaDTO<T> resposta = new RespostaDTO<T>();

		resposta.setCodigo(HTTP_CODE_BAD_REQUEST_NOT_FOUND);

		return resposta;
	}

	@Override
	public int getCodigo() {
		return codigo;
	}

	@Override
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public T getRetorno() {
		return retorno;
	}

	public void setRetorno(T retorno) {
		this.retorno = retorno;
	}

	public List<ErroDTO> getListaErro() {
		return listaErro;
	}

	public void setListaErro(List<ErroDTO> listaErro) {
		this.listaErro = listaErro;
	}

	public int getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
}
