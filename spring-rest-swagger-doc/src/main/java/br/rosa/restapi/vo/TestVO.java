package br.rosa.restapi.vo;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

public class TestVO {

	@Schema(name = "id of test", description = "id of die", example = "666", requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer id;
	private String name;
	private Date bornDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBornDate() {
		return bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}
}
