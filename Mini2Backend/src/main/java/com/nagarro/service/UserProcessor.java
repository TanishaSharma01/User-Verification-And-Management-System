package com.nagarro.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.nagarro.dto.Gender;
import com.nagarro.dto.Nationality;
import com.nagarro.dto.RandomUser;
import com.nagarro.entity.User;
import com.nagarro.service.fetchers.GenderDataFetcher;
import com.nagarro.service.fetchers.NationalityDataFetcher;

@Service
public class UserProcessor {

    // Process user data
    public User processUser(RandomUser randomUser, UserVerificationService verificationService,
                                  NationalityDataFetcher nationalityDataFetcher, GenderDataFetcher genderDataFetcher) {
        String firstName = randomUser.getResults().get(0).getName().getFirst();
        String lastName = randomUser.getResults().get(0).getName().getLast();
        String randomUserGender = randomUser.getResults().get(0).getGender();
        String randomUserNat = randomUser.getResults().get(0).getNat();
        String dateOfBirth = randomUser.getResults().get(0).getDob().getDate();
        int age = randomUser.getResults().get(0).getDob().getAge();

        CompletableFuture<Nationality> nationalityFuture = nationalityDataFetcher.fetchNationalityData(firstName);
        CompletableFuture<Gender> genderFuture = genderDataFetcher.fetchGenderData(firstName);

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(nationalityFuture, genderFuture);

        try {
            combinedFuture.get(); // Wait for both futures to complete
            Gender genderResult = genderFuture.get();
            Nationality nationalityResult = nationalityFuture.get();

            return handleNationalityAndGender(firstName, lastName, dateOfBirth, age, randomUserGender, randomUserNat,
                    nationalityResult, genderResult, verificationService);
        } catch (Exception e) {
            e.printStackTrace();
            handleError("Error in combination");
            return null;
        }
    }

    // Handle processing of nationality and gender
    private User handleNationalityAndGender(String firstName, String lastName, String dateOfBirth, int age,
                                                  String randomUserGender, String randomUserNat,
                                                  Nationality nationalityResult, Gender genderResult,
                                                  UserVerificationService verificationService) {
        boolean verified = verificationService.verifyUser(randomUserGender, randomUserNat, genderResult, nationalityResult);

        if (randomUserGender != null) {
            return createAndPopulateUser(firstName, lastName, dateOfBirth, age, randomUserGender, randomUserNat, verified);
        } else {
            System.out.println("No gender data found for Name: " + firstName);
            return null;
        }
    }

    // Create and populate a User object
    private User createAndPopulateUser(String firstName, String lastName, String dateOfBirth, int age,
                                       String randomUserGender, String randomUserNat, boolean verified) {
        User user = new User();
        String name = firstName + " " + lastName;
        user.setName(name);
        user.setNationality(randomUserNat);
        user.setAge(age);
        user.setGender(randomUserGender);
        // Formatting date of birth
        String dob = dateOfBirth.substring(0, 10);
        user.setDob(dob);

        if (verified) {
            user.setVerificationStatus("VERIFIED");
        } else {
            user.setVerificationStatus("TO_BE_VERIFIED");
        }
        
        return user;
    }

    private void handleError(String message) {
        System.out.println(message);
    }
}
