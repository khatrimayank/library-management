package com.SpringBoot.RestApi.UserManagement;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokensRepository extends JpaRepository<UserTokens,Long> {
	
	Optional<UserTokens> findByTokenAndIsDeletedFalse(String token);
}
