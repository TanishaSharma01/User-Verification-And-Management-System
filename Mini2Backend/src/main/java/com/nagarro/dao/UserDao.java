package com.nagarro.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nagarro.entity.User;

//User Repository
@Repository
public interface UserDao extends JpaRepository<User, Long>{

	//To find users according to offset and limit
	@Query(value = "SELECT * FROM User u ORDER BY u.id LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<User> findUsersWithOffsetAndLimit(@Param("offset") int offset, @Param("limit") int limit);
}
