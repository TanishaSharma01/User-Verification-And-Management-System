package com.nagarro.sorting.strategy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.nagarro.entity.User;
import com.nagarro.exceptions.CustomException;
import com.nagarro.sorting.SortingStrategy;

public class AgeSortingStrategy implements SortingStrategy {
    @Override
    public List<User> sort(List<User> randomUserlist, String sortOrder) throws CustomException {
    	// Implement age sorting logic here
    	try {
	        Comparator<User> ageComparator = Comparator.comparingInt(User::getAge).reversed();
	
	        //List of even ages
	        List<User> evenAges = randomUserlist.stream()
	                .filter(user -> user.getAge() % 2 == 0)
	                .sorted(ageComparator)
	                .collect(Collectors.toList());
	        
	        //List of odd ages
	        List<User> oddAges = randomUserlist.stream()
	                .filter(user -> user.getAge() % 2 != 0)
	                .sorted(ageComparator)
	                .collect(Collectors.toList());
	        
	        // Concatenate even and odd ages based on sort order
	        Stream<User> sortedStream;
	        
	        //sortOrder even
	        if ("even".equals(sortOrder)) {
	            sortedStream = Stream.concat(evenAges.stream(), oddAges.stream());
	        } 
	        //sortOrder odd
	        else if("odd".equals(sortOrder)){
	            sortedStream = Stream.concat(oddAges.stream(), evenAges.stream());
	        }
	        //if not even or odd
	        else {
	        	throw new CustomException("Sort Order input is invalid");
	        }
	        //return sorted list
	        return sortedStream.collect(Collectors.toList());
	    }
        catch(CustomException ex) {
        	 throw ex;

        }
	}
}
