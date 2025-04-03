package com.devsuperior.dsmovie.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsuperior.dsmovie.tests.TokenUtil;

import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

public class ScoreControllerRA {
	
	private Long existingMovieId, nonExistingMovieId;
	private String movieName;
	
	private Map<String, Object> postScoreInstance;
	
	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String clientToken, adminToken;
	
	@BeforeEach
	public void setUp() throws Exception{
		baseURI = "http://localhost:8080";
		movieName = "The Witcher";
		
		existingMovieId = 1L;
		nonExistingMovieId = 500L;
		
		clientUsername = "alex@gmail.com";
		clientPassword = "123456";
		
		adminUsername = "maria@gmail.com";
		adminPassword = "123456";
		
		clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword); 
		
		postScoreInstance = new HashMap<>();
	}
	
	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {	
		postScoreInstance.put("movieId", nonExistingMovieId);
		postScoreInstance.put("score", 4.0F);
		JSONObject newScore = new JSONObject(postScoreInstance);
		
		//Admin
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.body(newScore)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/scores")
		.then()
			.statusCode(404);
		
		//Client
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + clientToken)
			.body(newScore)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/scores")
			.then()
			.statusCode(404);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {
		postScoreInstance.put("movieId", null);
		postScoreInstance.put("score", 4.0F);
		JSONObject newScore = new JSONObject(postScoreInstance);
		
		//Admin
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.body(newScore)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/scores")
		.then()
			.statusCode(422);
		
		//Client
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + clientToken)
			.body(newScore)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/scores")
			.then()
			.statusCode(422);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {
		postScoreInstance.put("movieId", existingMovieId);
		postScoreInstance.put("score", -4.0F);
		JSONObject newScore = new JSONObject(postScoreInstance);
		
		//Admin
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.body(newScore)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/scores")
		.then()
			.statusCode(422);
		
		//Client
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + clientToken)
			.body(newScore)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/scores")
			.then()
			.statusCode(422);
	}
}
