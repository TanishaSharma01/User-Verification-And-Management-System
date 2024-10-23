package com.nagarro.sorting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.nagarro.entity.User;
import com.nagarro.exceptions.CustomException;
import com.nagarro.sorting.strategy.AgeSortingStrategy;
import com.nagarro.sorting.strategy.NameSortingStrategy;

/**
 * Service responsible for sorting user lists based on different sorting strategies.
 */
@Service
public class SortingService {
    // Map to store available sorting strategies
    private final Map<String, SortingStrategy> sortingStrategies;

    /**
     * Constructor to initialize sorting strategies.
     */
    public SortingService() {
        sortingStrategies = new HashMap<>();
        // Adding available sorting strategies
        sortingStrategies.put("name", new NameSortingStrategy());
        sortingStrategies.put("age", new AgeSortingStrategy());
        // Add more sorting strategies here if needed...
    }

    /**
     * Sorts a list of users based on the specified sorting strategy and order.
     *
     * @param userList   The list of users to be sorted.
     * @param sortType   The type of sorting strategy (e.g., "name", "age").
     * @param sortOrder  The order of sorting (e.g., "even", "odd").
     * @return A sorted list of users.
     * @throws CustomException If the sorting strategy is not found or other custom exceptions occur.
     */
    public List<User> sortUsers(List<User> userList, String sortType, String sortOrder) throws CustomException {
        try {
            // Get the sorting strategy based on the provided type
            SortingStrategy sortingStrategy = sortingStrategies.get(sortType.toLowerCase());
            
            // Check if the sorting strategy is available
            if (sortingStrategy == null) {
                throw new CustomException("Sort Type input is invalid");
            }

            // Use the sorting strategy to sort the user list
            return sortingStrategy.sort(userList, sortOrder.toLowerCase());
        } catch (CustomException ex) {
            // Propagate custom exceptions
            throw ex;
        }
    }
}