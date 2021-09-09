package services;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;


public class APIcall {
	@Before
	public void setup(){
		RestAssured.baseURI = "https://reqres.in";
		RestAssured.basePath = "/api";
		RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

		RestAssured.requestSpecification = new RequestSpecBuilder()
			.setContentType(ContentType.JSON)
			.build();
	}

	@Test
	public void loginTest(){

		given()
			.body("{\n" + "    \"email\": \"eve.holt@reqres.in\",\n"
				+ "    \"password\": \"cityslicka\"\n" + "}")
			.post("login")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body("token", notNullValue());
	}
}
