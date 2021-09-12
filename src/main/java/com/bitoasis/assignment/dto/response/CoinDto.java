package com.bitoasis.assignment.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class CoinDto implements Comparable<CoinDto>, Serializable {
	private static final long serialVersionUID = -9093632832386654205L;

	//private String id;
	private String name;
	
	@JsonAlias({ "symbol" })
	private String code;
	//private String website_slug;

	@Override
	public int compareTo(CoinDto c) {

		if (getName() == null || c.getName() == null) {
			return 0;
		}
		return getName().compareTo(c.getName());
	}
}
