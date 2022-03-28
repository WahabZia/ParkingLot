package com.parkinglot;


import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkinglot.dto.Car;


@SpringBootTest(classes =ParkingLotApplication.class , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql({ "/test.sql"})
class ParkingLotApplicationTests {

	@Autowired
	ObjectMapper mapper;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void testEnter() throws Exception {
		Car car = new Car();
		car.setRegNo("abc-123");
		car.setHeight(BigDecimal.TEN);
		car.setWeight(BigDecimal.valueOf(8));
		car.setModel("temp-model");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/enter")
				.content(this.mapper.writeValueAsString(car)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(mockRequest)
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()));
	}

	@Test
	public void testEnterFailCase() throws Exception {
		Car car = new Car();
		car.setRegNo("abc-123");
		car.setHeight(BigDecimal.valueOf(1000));
		car.setWeight(BigDecimal.valueOf(8));
		car.setModel("temp-model");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/enter")
				.content(this.mapper.writeValueAsString(car)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(mockRequest)
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()));
	
		car = new Car();
		car.setRegNo("abc-123");
		car.setModel("temp-model");
		 mockRequest = MockMvcRequestBuilders.post("/api/enter")
				.content(this.mapper.writeValueAsString(car)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(mockRequest)
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()));
	
		
	}

	@Test
	public void testExitFail() throws Exception {
		Car car = new Car();
		car.setRegNo("abc-124");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/exit")
				.content(this.mapper.writeValueAsString(car)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(mockRequest)
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()));
		
		car = new Car();
		car.setRegNo("");
		mockRequest = MockMvcRequestBuilders.post("/api/exit")
				.content(this.mapper.writeValueAsString(car)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(mockRequest)
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()));
		
	}
	
	@Test
	public void testExit() throws Exception {
		Car car = new Car();
		car.setRegNo("abc-123");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/exit")
				.content(this.mapper.writeValueAsString(car)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(mockRequest)
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()));
	}
	
}
