package com.picktime.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.picktime.dao.User;


@Repository
public interface UserRepository extends CrudRepository<User, String> {
    
    @Override
    public void delete(User user);
    
    Optional<User> findByFirstname(String firstname);

	Boolean existsByFirstname(String firstname);
	
	@Query("from Users c where c.firstname=:firstname and c.lastname=:lastname and c.password=:password ")
	Boolean checkUser(String firstname,String lastname,String password);
	
	@Query("from Users c where c.firstname=:firstname and c.lastname=:lastname ")
	Optional<User> getUser(String firstname,String lastname);
	
	Optional<User> findByFirstnameAndLastname(String firstname,String lastname);
    
}