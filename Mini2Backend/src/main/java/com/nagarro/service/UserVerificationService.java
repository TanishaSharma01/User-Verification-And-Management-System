package com.nagarro.service;

import com.nagarro.dto.Gender;
import com.nagarro.dto.Nationality;

import org.springframework.stereotype.Service;

@Service
public class UserVerificationService {

	/**
     * Verifies the user based on gender and nationality data.
     *
     * @param randomUserGender Gender from the random user data.
     * @param randomUserNat    Nationality from the random user data.
     * @param gender            Gender data from Gender API.
     * @param nationality       Nationality data from nationality API.
     * @return True if the user is verified, false otherwise.
     */
    public boolean verifyUser(String randomUserGender, String randomUserNat, Gender gender, Nationality nationality) {
        // Check if the provided nationality data is available
        if (nationality != null && nationality.getCountry() != null && !nationality.getCountry().isEmpty()) {
            // Loop through the country list to find a match
            for (Nationality.Country country : nationality.getCountry()) {
                String countryId = country.getCountryId();
                // Check if gender and nationality match
                if (randomUserGender.equals(gender.getGender()) && randomUserNat.equals(countryId)) {
                	System.out.println("Gender matched: "+randomUserGender+" "+gender.getGender());
                	System.out.println("Nationality macthed: "+randomUserNat+" "+countryId);
                    return true; // User is verified
                }
            }
        }
        return false; // User is not verified
    }
}

