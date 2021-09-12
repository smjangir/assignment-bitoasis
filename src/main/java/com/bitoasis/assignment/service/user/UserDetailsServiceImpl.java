package com.bitoasis.assignment.service.user;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bitoasis.assignment.dto.user.CustomUserDetails;
import com.bitoasis.assignment.entity.user.User;
import com.bitoasis.assignment.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Inside "+this.getClass().getName());
		Optional<User> user = this.userRepository.findByUsernameOrEmail(username);
		if (user.isPresent()) {
			log.info("Inside "+this.getClass().getName() +" user present ");
			CustomUserDetails customUserDetails = new CustomUserDetails(user.get());
			return customUserDetails;
		}
		throw new UsernameNotFoundException("Could not found user !!");
	}

}
