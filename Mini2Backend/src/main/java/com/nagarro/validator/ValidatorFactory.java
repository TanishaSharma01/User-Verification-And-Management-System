package com.nagarro.validator;

import com.nagarro.validator.type.EnglishAlphabetsValidator;
import com.nagarro.validator.type.NumericValidator;

//ValidatorFactory class
public class ValidatorFactory {

 /**
  * Gets the appropriate validator based on the type of input.
  *
  * @param input The input for which a validator is needed.
  * @return Validator instance based on the type of input.
  */
 public static Validator getValidator(String input) {
     // If input is numeric
     if (input.matches("\\d+")) {
         return NumericValidator.getInstance();
     }
     // If input contains English alphabets
     else {
         return EnglishAlphabetsValidator.getInstance();
     }
 }
}
