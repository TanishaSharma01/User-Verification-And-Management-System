package com.nagarro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.dto.Payload;
import com.nagarro.dto.RandomUserList;
import com.nagarro.entity.User;
import com.nagarro.exceptions.CustomException;
import com.nagarro.service.UserService;
import com.nagarro.validator.Validator;
import com.nagarro.validator.ValidatorFactory;
import com.nagarro.exceptions.GlobalExceptionHandler;

/**
 * Controller class to handle user-related HTTP requests.
 */
@RestController
public class UserController {

    private final UserService userService;
    private final GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    public UserController(UserService userService, GlobalExceptionHandler globalExceptionHandler) {
        this.userService = userService;
        this.globalExceptionHandler = globalExceptionHandler;
    }

    /**
     * Handles the HTTP POST request to fetch random user data.
     *
     * @param payload Payload object containing the requested number of users.
     * @return ResponseEntity containing the last N users fetched.
     */
    @PostMapping("/users")
    public ResponseEntity<?> getUserData(@RequestBody Payload payload) {
        try {
            if (payload.getPayload() >= 1 && payload.getPayload() <= 5) {
                userService.fetchRandomUserData(payload.getPayload());
                List<User> lastNUsers = userService.getLastNUsers(payload.getPayload());
                return ResponseEntity.ok(lastNUsers);
            } else {
                throw new CustomException("Please enter a payload between 1 and 5.");
            }
        } catch (CustomException ex) {
            return globalExceptionHandler.handleCustomException(ex);
        } catch (Exception ex) {
            return globalExceptionHandler.handleException(ex);
        }
    }

    /**
     * Handles the HTTP GET request to get a list of users with optional sorting,
     * pagination, and filtering.
     *
     * @param sortType  The type of sorting to be applied.(name,age)
     * @param sortOrder The order of sorting (even, odd).
     * @param limit     The maximum number of users to return.
     * @param offset    The users to be skipped.
     * @return ResponseEntity containing the sorted and paginated list of users.
     * @throws CustomException If validation or other business logic fails.
     */
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(
            @RequestParam(required = false) String sortType,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false, defaultValue = "5") String limit,
            @RequestParam(required = false, defaultValue = "0") String offset
    ) throws CustomException {

        try {
            // Validate input parameters
            validateInputParameters(sortType, sortOrder, limit, offset);

            int limit1 = Integer.parseInt(limit);
            int offset1 = Integer.parseInt(offset);

            RandomUserList users = userService.getSortedUsers(sortType, sortOrder, limit1, offset1);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (NumberFormatException ex) {
            throw new CustomException("Limit and offset must be valid integers");
        } catch (CustomException ex) {
            return globalExceptionHandler.handleCustomException(ex);
        } catch (Exception ex) {
            return globalExceptionHandler.handleException(ex);
        }
    }

    /**
     * Validates the input parameters for sorting, pagination, and filtering.
     *
     * @param sortType  The type of sorting to be applied.
     * @param sortOrder The order of sorting (ASC or DESC).
     * @param limit     The maximum number of users to return.
     * @param offset    The offset for pagination.
     * @throws CustomException If validation fails.
     */
    private void validateInputParameters(String sortType, String sortOrder, String limit, String offset)
            throws CustomException {
        // Validate sortType, sortOrder, limit, and offset
        Validator sortTypeValidator = ValidatorFactory.getValidator(sortType);
        Validator sortOrderValidator = ValidatorFactory.getValidator(sortOrder);
        Validator limitValidator = ValidatorFactory.getValidator(String.valueOf(limit));
        Validator offsetValidator = ValidatorFactory.getValidator(String.valueOf(offset));

        if (!sortTypeValidator.validate(sortType)) {
            throw new CustomException("Sort Type input is invalid");
        } else if (!sortOrderValidator.validate(sortOrder)) {
            throw new CustomException("Sort Order input is invalid");
        } else if (!limitValidator.validate(limit)) {
            throw new CustomException("Limit input is invalid");
        } else if (!offsetValidator.validate(offset)) {
            throw new CustomException("Offset input is invalid");
        }

        int limit1 = Integer.parseInt(limit);
        int offset1 = Integer.parseInt(offset);

        if (limit1 < 1 || limit1 > 5 || offset1 < 0) {
            throw new CustomException("Limit should be between 1 and 5.");
        }
    }
}
