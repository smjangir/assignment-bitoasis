package com.bitoasis.assignment.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTickerResponseDto {

	private String code;
	private BigDecimal price;
	private long volume;
	private BigDecimal daily_change;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime last_updated;
	 
}
