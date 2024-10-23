package com.nagarro.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nagarro.dto.RandomUserList;
import com.nagarro.entity.User;
import com.nagarro.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	
	//POST test for valid payload
	@Test
	public void testPostUserData_ValidPayload() throws Exception {
	    // Mock the service method
//	    doNothing().when(userService).fetchRandomUserData(anyInt());
	    when(userService.getLastNUsers(anyInt())).thenReturn(List.of(new User())); // Replace with your actual implementation
	 
	    // Perform the POST request
	    mockMvc.perform(MockMvcRequestBuilders.post("/users")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\"payload\": 3}")
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1)); // Assuming your response is a JSON array
	 
	    // Verify that the service methods were called
	    verify(userService, times(1)).fetchRandomUserData(3);
	    verify(userService, times(1)).getLastNUsers(3);
	}
	
	//POST test for invalid payload
	@Test
	public void testPostUserData_InvalidPayload() throws Exception {
	    // Perform the POST request with an invalid payload
	    mockMvc.perform(MockMvcRequestBuilders.post("/users")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\"payload\": 6}")
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest());
	    
	    // Verify that the service methods were not called
	    verify(userService, never()).fetchRandomUserData(anyInt());
	    verify(userService, never()).getLastNUsers(anyInt());
	}
 
	//POST test for invalid payload
	@Test
	public void testPostUserData_NullPayload() throws Exception {
	    // Perform the POST request with an invalid payload
	    mockMvc.perform(MockMvcRequestBuilders.post("/users")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{}")
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest());
	    
	    // Verify that the service methods were not called
	    verify(userService, never()).fetchRandomUserData(anyInt());
	    verify(userService, never()).getLastNUsers(anyInt());
	}
	
	//POST test for invalid payload
	@Test
	public void testPostUserData_NoBody() throws Exception {
	    // Perform the POST request with an invalid payload
	    mockMvc.perform(MockMvcRequestBuilders.post("/users")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("")
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isInternalServerError());
	    
	    // Verify that the service methods were not called
	    verify(userService, never()).fetchRandomUserData(anyInt());
	    verify(userService, never()).getLastNUsers(anyInt());
	}
		
	//GET test for Valid Parameters
	@Test
	public void testGetUsers_Valid() throws Exception {
		// Mock the service method
	    when(userService.getSortedUsers(anyString(), anyString(), anyInt(), anyInt())).thenReturn(new RandomUserList());
	 
	    // Perform the GET request
	    mockMvc.perform(MockMvcRequestBuilders.get("/users")
	    		.param("sortType", "name")
	            .param("sortOrder", "even")
	            .param("limit", "3")
	            .param("offset", "1")
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk());
	 
	    // Verify that the service method was called
	    verify(userService, times(1)).getSortedUsers("name", "even", 3, 1);
	}
	
	//GET test where NO value provided for offset and limit, so default limit=5, offload=0
	@Test
	public void testGetUsers_DefaulLimitAndOffset() throws Exception {
		// Mock the service method
	    when(userService.getSortedUsers(anyString(), anyString(), anyInt(), anyInt())).thenReturn(new RandomUserList());
	 
	    // Perform the GET request
	    mockMvc.perform(MockMvcRequestBuilders.get("/users")
	    		.param("sortType", "name")
	            .param("sortOrder", "even")
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk());
	 
	    // Verify that the service method was called
	    verify(userService, times(1)).getSortedUsers("name", "even", 5, 0);
	}

	//
	@Test
	public void testGetUsers_InvalidLimit() throws Exception {
		// Perform the GET request
    	mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .param("sortType", "name")
                .param("sortOrder", "even")
                .param("limit", "6")
                .param("offset", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	//GET test for Invalid Sort Type and Sort Order
    @Test
    public void testGetUsers_InvalidSortTypeSortOrder() throws Exception {
    	
        // Perform the GET request
    	mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .param("sortType", "ndndnd")
                .param("sortOrder", "")
                .param("limit", "3")
                .param("offset", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    //GET test for Invalid Limit and Offset
	@Test
	public void testGetUsers_InvalidLimitOffset() throws Exception {
		// Perform the GET request with invalid limit and offset
		mockMvc.perform(MockMvcRequestBuilders.get("/users")
				.param("sortType", "name")
				.param("sortOrder", "even")
				.param("limit", "abcd")
				.param("offset", "-1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}

