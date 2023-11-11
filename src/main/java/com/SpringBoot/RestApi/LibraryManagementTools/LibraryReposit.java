package com.SpringBoot.RestApi.LibraryManagementTools;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryReposit extends JpaRepository<BooksDataTable,Integer> {

}
