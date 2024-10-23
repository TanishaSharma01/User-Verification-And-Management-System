package com.nagarro.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

//Nationality DTO
public class Nationality {
    private int count;
    private String name;
    private List<Country> country;

    // Constructors, getters, and setters

    public Nationality() {
    }

    public Nationality(int count, String name, List<Country> country) {
        this.count = count;
        this.name = name;
        this.country = country;
    }

    // Getters and setters

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }

    public static class Country {
        @JsonProperty("country_id")  // Add this line
        private String countryId;
        private double probability;

        // Constructors, getters, and setters

        public Country() {
        }

        public Country(String countryId, double probability) {
            this.countryId = countryId;
            this.probability = probability;
        }

        // Getters and setters

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public double getProbability() {
            return probability;
        }

        public void setProbability(double probability) {
            this.probability = probability;
        }

        @Override
        public String toString() {
            return "Country [countryId=" + countryId + ", probability=" + probability + "]";
        }
    }


	@Override
	public String toString() {
		return "Nationality [count=" + count + ", name=" + name + ", country=" + country + "]";
	}
    
}
