package com.nagarro.dto;

import java.util.List;

//Random User DTO
public class RandomUser {
	
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public RandomUser(List<Result> results) {
        super();
        this.results = results;
    }

    public RandomUser() {
        super();
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static class Result {
        private Name name;
        private Dob dob;
        private String gender; 
        private String nat; 

        public Name getName() {
            return name;
        }

        public Dob getDob() {
            return dob;
        }

        public String getGender() {
            return gender;
        }

        public String getNat() {
            return nat;
        }
    }

    public static class Name {
        private String title;
        private String first;
        private String last;

        public String getTitle() {
            return title;
        }

        public String getFirst() {
            return first;
        }

        public String getLast() {
            return last;
        }
    }

    public static class Dob {
        private String date;
        private int age;

        public String getDate() {
            return date;
        }

        public int getAge() {
            return age;
        }
    }
}