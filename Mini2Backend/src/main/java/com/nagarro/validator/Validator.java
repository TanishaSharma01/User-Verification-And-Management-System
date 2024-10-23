package com.nagarro.validator;

//Validator
public interface Validator {
	/**
     * Validates the given input.
     * 
     * @param input The input to be validated.
     * @return true if the input is valid, false otherwise.
     */
	boolean validate(String input);
}
