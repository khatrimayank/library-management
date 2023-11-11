package com.SpringBoot.RestApi.UserManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringBoot.RestApi.UserManagement.Entity.UserTokens;

public interface UserTokensRepository extends JpaRepository<UserTokens,Long> {

}
