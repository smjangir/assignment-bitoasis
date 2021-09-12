package com.bitoasis.assignment.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {

	private String token;

}
