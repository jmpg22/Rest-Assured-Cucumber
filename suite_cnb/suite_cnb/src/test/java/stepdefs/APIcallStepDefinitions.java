package stepdefs;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import cucumber.api.java.en.And;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.springframework.context.annotation.PropertySource;

import java.sql.SQLOutput;

//@PropertySource("classpath:application.properties")
public class APIcallStepDefinitions {

	private static RequestSpecification requ;

	//@Value("${api.url}")
	 String api_uri = "https://reqres.in";
	//@Value("${api.resource}")
	 String api_resource = "/api/users";

	@Before
	public void setup(){
		RestAssured.baseURI = this.api_uri;
		RestAssured.basePath = this.api_resource;
		RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

		RestAssured.requestSpecification = new RequestSpecBuilder()
			.setContentType(ContentType.JSON)
			.build();
	}

	@Given("a user retrieves the API")
	public void aUserRetrievesTheAPI() throws Exception{
		requ = (RequestSpecification) new RequestSpecBuilder().setBaseUri(api_uri).build();
	}

	@When("^a user with id \"(.*)\"$")
	public void a_user_with_id(int id1) {
		Response response = (Response) given()
			.spec(requ)
			.when()
			.get(api_resource)
			.then()
			.contentType(ContentType.JSON)
			.extract()
			.response();

		id1 = response.path("data.id[1]");
		assertNotNull(id1);
		//assertEquals(id1,new Integer(2));
		System.out.println("+++++++++++++++++++++++ All displayed here: ++++++++++++++++++++\n"+response);
	}

	@Then("the status code is (.*)")
	public void theStatusCodeIs(int status_code) {
		given()
			.spec(requ)
			.when()
			.get(api_resource)
			.then()
			.assertThat().
			statusCode(HttpStatus.SC_OK);
	}

}


