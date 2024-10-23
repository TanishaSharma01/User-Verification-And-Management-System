package com.nagarro.service;

import java.util.*;

import com.nagarro.dto.RandomUserList;
import com.nagarro.entity.User;
import com.nagarro.exceptions.CustomException;

//User Service Interface
public interface UserService extends UserDataFetchingService, UserRetrievalService {
}

//User Service
interface UserDataFetchingService {
	// To fetch "payload" number of users
	void fetchRandomUserData(int payload);
}

//User Retrieval Interface
interface UserRetrievalService {
	// To get all users
	List<User> getAllUser();

	// To get List of Sorted Users
	RandomUserList getSortedUsers(String sortType, String sortOrder, int limit, int offset) throws CustomException;

	// To get latest N users in descending order
	List<User> getLastNUsers(int n);

	// To get count of all users
	long getCountAllUsers();
}
