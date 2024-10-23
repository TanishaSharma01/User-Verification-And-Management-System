package com.nagarro.sorting;

import java.util.List;

import com.nagarro.entity.User;
import com.nagarro.exceptions.CustomException;

//Sorting Strategy
public interface SortingStrategy {
    //To sort users based on their Sort type and order
	List<User> sort(List<User> userList, String sortOrder) throws CustomException;
}
