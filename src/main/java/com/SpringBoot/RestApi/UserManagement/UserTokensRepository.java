package com.SpringBoot.RestApi.UserManagement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokensRepository extends JpaRepository<UserTokens,Long> {

}
