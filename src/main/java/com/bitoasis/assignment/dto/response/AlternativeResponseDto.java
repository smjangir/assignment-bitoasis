package com.bitoasis.assignment.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class AlternativeResponseDto implements Serializable{

	private static final long serialVersionUID = -5536238947246570953L;
	public List<CoinDto> data;
	public Object metadata;
}
