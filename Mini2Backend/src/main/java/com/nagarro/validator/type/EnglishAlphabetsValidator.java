package com.nagarro.validator.type;

import com.nagarro.validator.Validator;

//EnglishAlphabetsValidator singleton class
public class EnglishAlphabetsValidator implements Validator {

	 // Singleton instance of EnglishAlphabetsValidator
	 private static final EnglishAlphabetsValidator instance = new EnglishAlphabetsValidator();
	
	 // Private constructor to enforce singleton pattern
	 private EnglishAlphabetsValidator() {
	 }
	
	 /**
	  * Gets the singleton instance of EnglishAlphabetsValidator.
	  *
	  * @return EnglishAlphabetsValidator instance.
	  */
	 public static EnglishAlphabetsValidator getInstance() {
	     return instance;
	 }
	
	 /**
	  * Validates whether the input consists of English alphabetic characters.
	  *
	  * @param input The input string to be validated.
	  * @return True if the input contains only English alphabets, false otherwise.
	  */
	 @Override
	 public boolean validate(String input) {
	     return input.matches("[a-zA-Z]+");
	 }
}
