package com.nagarro.dto;

//Gender DTO
public class Gender {
	private int count;
    private String name;
    private String gender;
    private double probability;

    // Constructors, getters, and setters

    public Gender() {
    }

    public Gender(int count, String name, String gender, double probability) {
        this.count = count;
        this.name = name;
        this.gender = gender;
        this.probability = probability;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

	@Override
	public String toString() {
		return "Gender [count=" + count + ", name=" + name + ", gender=" + gender + ", probability=" + probability
				+ "]";
	}
    
}
