package com.ry.demo.elevatormgr.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ry.demo.elevatormgr.AppConfig;
import com.ry.demo.elevatormgr.AppInitializer;
import com.ry.demo.elevatormgr.ElevatorManagerApplication;
import com.ry.demo.elevatormgr.model.Elevator;
import com.ry.demo.elevatormgr.model.Floor;
import com.ry.demo.elevatormgr.service.ElevatorService;

@WebMvcTest(ElevatorController.class)
@ContextConfiguration(classes={ElevatorManagerApplication.class, AppInitializer.class, AppConfig.class})
public class ElevatorControllerTest {

	@Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @SpyBean
    ElevatorService service;
    
    Elevator testElevator_1 = null;
    Elevator testElevator_2 = null;
    
    @BeforeEach
    public void setUp() {
    	testElevator_1 = new Elevator("public", 1000.0);
        testElevator_2 = new Elevator("freight", 3000.0);
        
        HashMap<Floor, Boolean> publicFloorsMap = new HashMap<Floor, Boolean>();
    	publicFloorsMap.put(new Floor(-1), true);
    	int i = 0;
    	while (i <= 49) {
    		publicFloorsMap.put(new Floor(i++), false);
    	}
    	publicFloorsMap.put(new Floor(50), true);
    	testElevator_1.setAvailableFloors(publicFloorsMap);
    	
    	i = -1;
    	HashMap<Floor, Boolean> freightFloorsMap = new HashMap<Floor, Boolean>();
    	while (i <= 50) {
    		freightFloorsMap.put(new Floor(i++), false);
    	}
    	testElevator_2.setAvailableFloors(freightFloorsMap);
    }
	
	@Test
	public void GetElevator_200_ReturnElevatorData() throws Exception {
		Mockito.when(service.getElevatorByDenomination("public")).thenReturn(testElevator_1);
		
		mockMvc.perform(get("/elevators/public")
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.denomination", is("public")));
	}
	
	@Test
	public void GetElevator_404_ShouldReturnNotFoundError() throws Exception {
		mockMvc.perform(get("/elevators/nonExistant"))
			.andDo(print()).andExpect(status().isNotFound());
	}
	
	@Test
	public void UpdateElevator_204_UpdateEntityAndReturnNoContent() throws Exception {
		testElevator_1.setCurrentFloor(15);
		testElevator_1.setCurrentWeight(70.0);
		
		Mockito.when(service.getElevatorByDenomination("public")).thenReturn(testElevator_1);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.patch("/elevators/public")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(testElevator_1));
		
		mockMvc.perform(mockRequest)
			.andExpect(status().isNoContent())
			.andDo(print());
	}
	
	@Test
	public void UpdateElevator_401_FailedKeycardAuthentication() throws Exception {
		testElevator_1.setCurrentFloor(50);
		testElevator_1.setCurrentWeight(70.0);
		
		Mockito.when(service.getElevatorByDenomination("public")).thenReturn(testElevator_1);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.patch("/elevators/public")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(testElevator_1));
		
		mockMvc.perform(mockRequest)
			.andExpect(status().isForbidden())
			.andDo(print());
	}
	
	@Test
	public void UpdateElevator_203_SuccessfulKeycardAuthentication() throws Exception {
		testElevator_1.setCurrentFloor(50);
		testElevator_1.setCurrentWeight(70.0);
		
		Mockito.when(service.getElevatorByDenomination("public")).thenReturn(testElevator_1);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.patch("/elevators/public")
				.header(HttpHeaders.AUTHORIZATION, "Basic YWRtaW46YWRtaW4xMjM=")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(testElevator_1));
		
		mockMvc.perform(mockRequest)
			.andExpect(status().isNoContent())
			.andDo(print());
	}
	
	@Test
	public void UpdateElevator_203_SkipsAuthentication() throws Exception {
		testElevator_2.setCurrentFloor(50);
		testElevator_2.setCurrentWeight(70.0);
		
		Mockito.when(service.getElevatorByDenomination("freight")).thenReturn(testElevator_2);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.patch("/elevators/freight")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(testElevator_2));
		
		mockMvc.perform(mockRequest)
			.andExpect(status().isNoContent())
			.andDo(print());
	}
}
