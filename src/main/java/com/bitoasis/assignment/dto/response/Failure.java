package com.bitoasis.assignment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Failure {
	
	public String field;
	public Object rejectedValue;
	public Object message;
	
}
