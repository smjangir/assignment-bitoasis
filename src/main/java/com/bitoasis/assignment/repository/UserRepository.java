package com.bitoasis.assignment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bitoasis.assignment.entity.user.User;


public interface UserRepository extends JpaRepository<User, Long> {

	public  User findByUsername(String username);

	@Query(value = "select * from users  where username = :username", nativeQuery = true)
	public Optional<User> getUserByUserName(@Param("username") String username);
	
	@Query(value = "select * from users  where email = :email", nativeQuery = true)
	public Optional<User> getUserByEmail(@Param("email") String email);
	
	@Query(value = "select * from users  where username = :username or email = :username", nativeQuery = true )
	public Optional<User> findByUsernameOrEmail(@Param("username") String username);

}
