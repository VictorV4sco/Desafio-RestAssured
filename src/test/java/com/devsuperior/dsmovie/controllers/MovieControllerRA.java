package com.devsuperior.dsmovie.controllers;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class MovieControllerRA {
	
	private Long existingMovieId, nonExistingMovieId;
	
	@BeforeEach
	public void setUp() {
		baseURI = "http://localhost:8080";
	}
	
	@Test
	public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {
	}
	
	@Test
	public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {		
	}
	
	@Test
	public void findByIdShouldReturnMovieWhenIdExists() {		
		existingMovieId = 2L;
		
		given()
			.get("/movies/{id}", existingMovieId)
		.then()
			.statusCode(200)
			.body("id", is(2))
			.body("title", equalTo("Venom: Tempo de Carnificina"))
			.body("score", is(3.3F))
			.body("count", is(3))
			.body("image", equalTo("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/vIgyYkXkg6NC2whRbYjBD7eb3Er.jpg"));
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {	
		nonExistingMovieId = 500L;
		
		given()
			.get("/movies/{id}", nonExistingMovieId)
		.then()
			.statusCode(404);
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() throws JSONException {		
	}
	
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
	}
	
	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {
	}
}
