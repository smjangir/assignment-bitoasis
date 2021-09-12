package com.bitoasis.assignment.config;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bitoasis.assignment.service.user.UserDetailsServiceImpl;
import com.bitoasis.assignment.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	UserDetailsServiceImpl customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestTokenHeader = request.getHeader("Authorization");
		if(Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")) {
			if (requestTokenHeader.startsWith("Bearer ")) {
				String jwtToken = requestTokenHeader.substring(7);
				try {
					String username = this.jwtUtil.extractUsername(jwtToken);
					UserDetails loadUserByUsername = this.customUserDetailsService.loadUserByUsername(username);
					if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
								new UsernamePasswordAuthenticationToken(loadUserByUsername, null, loadUserByUsername.getAuthorities());
						usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					} else {
						log.info("Token is not validated..");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
