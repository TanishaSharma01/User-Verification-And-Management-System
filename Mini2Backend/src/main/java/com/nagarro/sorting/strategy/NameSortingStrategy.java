package com.nagarro.sorting.strategy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.nagarro.entity.User;
import com.nagarro.exceptions.CustomException;
import com.nagarro.sorting.SortingStrategy;

public class NameSortingStrategy implements SortingStrategy {
	@Override
    public List<User> sort(List<User> userList, String sortOrder) throws CustomException {
    	// Implement name sorting logic here
    	try {
	        Comparator<User> nameLengthComparator = Comparator.comparingInt((User user) -> user.getName().length()).reversed();
	
	        //List with even letters in name
	        List<User> evenNameLengths = userList.stream()
	                .filter(user -> user.getName().length() % 2 == 0)
	                .sorted(nameLengthComparator)
	                .collect(Collectors.toList());
	
	        //List with odd letters in name
	        List<User> oddNameLengths = userList.stream()
	                .filter(user -> user.getName().length() % 2 != 0)
	                .sorted(nameLengthComparator)
	                .collect(Collectors.toList());
	
	        // Concatenate even and odd name lengths based on sort order
	        List<User> sortedList;
	        //sortOrder even
	        if ("even".equals(sortOrder)) {
	            sortedList = Stream.concat(evenNameLengths.stream(), oddNameLengths.stream())
	                    .collect(Collectors.toList());
	        } 
	        //sortOrder odd
	        else if("odd".equals(sortOrder)) {
	            sortedList = Stream.concat(oddNameLengths.stream(), evenNameLengths.stream())
	                    .collect(Collectors.toList());
	        }
	        //if not "even" or "odd"
	        else {
	        	throw new CustomException("Sort Order input is invalid");
		        }
	
		        return sortedList;

	    }
		catch(CustomException ex) {
			throw ex;
		}
	}
}
