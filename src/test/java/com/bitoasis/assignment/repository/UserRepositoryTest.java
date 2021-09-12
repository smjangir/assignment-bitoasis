package com.bitoasis.assignment.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.bitoasis.assignment.entity.user.User;
import com.bitoasis.assignment.repository.UserRepository;


@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepo;

	@Test
	@Order(1)
	@Rollback(value = false)
	public void saveUserTest() {
		User user = User.builder().username("shubh").email("shubham@gmail.com").name("Shubham").password("shubham")
				.role("ROLE_USER").build();
		User save = userRepo.save(user);
		
		Assertions.assertThat(user.getId()).isGreaterThan(0);
	}

	@Test
	@Order(2)
	@Rollback(value = false)
	public void findByIdTest() {
		User user = userRepo.getUserByEmail("shubham@gmail.com").get();
		Assertions.assertThat(user.getEmail()).isEqualTo("shubham@gmail.com");
	}
	
	@Test
	@Order(3)
	@Rollback(value = false)
	public void deleteByIdTest() {
		User user = userRepo.getUserByUserName("shubh").get();
		userRepo.delete(user);
		Assertions.assertThat(userRepo.existsById(user.getId())).isFalse();
	}
}
