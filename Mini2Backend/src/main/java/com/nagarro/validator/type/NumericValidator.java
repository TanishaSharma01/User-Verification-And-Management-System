package com.nagarro.validator.type;

import com.nagarro.validator.Validator;

//NumericValidator singleton class
public class NumericValidator implements Validator {
	 // Singleton instance of NumericValidator
	 private static final NumericValidator instance = new NumericValidator();
	
	 // Private constructor to enforce singleton pattern
	 private NumericValidator() {
	 }
	
	 /**
	  * Gets the singleton instance of NumericValidator.
	  *
	  * @return NumericValidator instance.
	  */
	 public static NumericValidator getInstance() {
	     return instance;
	 }
	
	 /**
	  * Validates whether the input consists of numeric characters.
	  *
	  * @param input The input string to be validated.
	  * @return True if the input is numeric, false otherwise.
	  */
	 @Override
	 public boolean validate(String input) {
	     return input.matches("\\d+");
	 }
}

