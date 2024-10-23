package com.nagarro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.nagarro.dao.UserDao;
import com.nagarro.dto.RandomUser;
import com.nagarro.dto.RandomUserList;
import com.nagarro.entity.User;
import com.nagarro.exceptions.CustomException;
import com.nagarro.service.fetchers.GenderDataFetcher;
import com.nagarro.service.fetchers.NationalityDataFetcher;
import com.nagarro.service.fetchers.UserDataFetcher;
import com.nagarro.sorting.SortingService;

@Service
public class UserServiceImpl implements UserService {

    private final UserDataFetcher userDataFetcher;
    private final NationalityDataFetcher nationalityDataFetcher;
    private final GenderDataFetcher genderDataFetcher;
    private final UserVerificationService verificationService;
    private final UserProcessor userProcessor;
	private UserDao repo;
	private SortingService sortingService;

    @Autowired
    public UserServiceImpl(UserDataFetcher userDataFetcher,
                           NationalityDataFetcher nationalityDataFetcher,
                           GenderDataFetcher genderDataFetcher,
                           UserVerificationService verificationService,
                           UserProcessor userProcessor,
                           UserDao repo,
                           SortingService sortingService) {
        this.userDataFetcher = userDataFetcher;
        this.nationalityDataFetcher = nationalityDataFetcher;
        this.genderDataFetcher = genderDataFetcher;
        this.verificationService = verificationService;
        this.userProcessor = userProcessor;
        this.repo = repo;
        this.sortingService=sortingService;
    }
    
    /**
     * Fetches random user data and processes it to save in the repository.
     *
     * @param payload Number of threads to use for fetching data.
     */
    @Override
    public void fetchRandomUserData(int payload) {
        validatePayload(payload);

        ExecutorService executorService = Executors.newFixedThreadPool(payload);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < payload; i++) {
        	CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    RandomUser randomUser = userDataFetcher.fetchRandomUserData();
                    processAndSaveUser(randomUser);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }, executorService);

            futures.add(future);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();

            executorService.shutdown();
    }
    
    //to validate payload
    private void validatePayload(int payload) {
        if (payload < 1 || payload > 5) {
            throw new IllegalArgumentException("Payload should be between 1 and 5");
        }
    }
    
    //to process and save user
    private void processAndSaveUser(RandomUser randomUser) {
        if (randomUser != null && randomUser.getResults() != null && !randomUser.getResults().isEmpty()) {
            User user = userProcessor.processUser(randomUser, verificationService, nationalityDataFetcher, genderDataFetcher);
            if (user != null) {
                repo.save(user);
            }
        } else {
            System.out.println("No result found in response");
        }
    }
    
    /**
     * Retrieves all users from the repository.
     *
     * @return List of all users.
     */
    public List<User> getAllUser() {
        return repo.findAll();
    }
    
    /**
     * Retrieves a paginated and sorted list of users.
     *
     * @param sortType  Type of sorting (e.g., name, age).
     * @param sortOrder Sorting order (e.g., even, odd).
     * @param limit     Number of users to retrieve.
     * @param offset    Offset the number of users.
     * @return Paginated and sorted list of users.
     * @throws CustomException If an exception occurs during the operation.
     */
    @Override
    public RandomUserList getSortedUsers(String sortType, String sortOrder, int limit, int offset) throws CustomException {
    	List<User> randomUsers = repo.findUsersWithOffsetAndLimit(offset, limit);
        randomUsers = sortingService.sortUsers(randomUsers, sortType, sortOrder);
        return buildRandomUserList(randomUsers, offset, limit);
    }
    
    //to build RandomUserList
    private RandomUserList buildRandomUserList(List<User> randomUsers, int offset, int limit) {
        RandomUserList userList = new RandomUserList();
        userList.setRandomuser(randomUsers);
        setPagingInfo(userList, offset, limit);
        return userList;
    }
    
    /**
     * Sets paging information in the provided {@code userList}.
     *
     * @param userList List of users with paging information.
     * @param offset   Offset for paginated results.
     * @param limit    Number of users to retrieve.
     */
    private void setPagingInfo(RandomUserList userList, int offset, int limit) {
        long count = getCountAllUsers();
        userList.getPageinfo().setHasNext((offset + limit) < count);
        userList.getPageinfo().setHasPrevious(offset > 0);
        userList.getPageinfo().setTotal(getCountAllUsers());
    }
    
    
    /**
     * Retrieves the last N users from the repository.
     *
     * @param n Number of users to retrieve.
     * @return List of the last N users.
     */
    @Override
    public List<User> getLastNUsers(int n) {
        Sort sortByCreatedAtDesc = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, n, sortByCreatedAtDesc);
        return repo.findAll(pageable).getContent();
    }

    /**
     * Retrieves the total count of all users in the repository.
     *
     * @return Total count of users.
     */
    @Override
    public long getCountAllUsers() {
        return repo.count();
    }
        
}
