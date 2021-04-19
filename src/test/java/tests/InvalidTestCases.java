package tests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class InvalidTestCases {
	
	private static RequestSpecification requestSpec;
	

	@BeforeSuite
	public static void createRequestSpecification() {

		requestSpec = new RequestSpecBuilder().
				setBaseUri("https://jsonplaceholder.typicode.com").build();
		
	}
	@Test(priority = 1)
	public void invalidGetRequestURL() {
		given().spec(requestSpec).
		get("postss").then().assertThat().statusCode(404);

	}

	@Test(priority = 2) 
	public void invalidParmeter() {

		given().spec(requestSpec).when().get("posts/101").
		then().assertThat().statusCode(404);
	}
	@Test(priority = 3)
	public void invalidRequestType() {
		given().spec(requestSpec).
		accept(ContentType.JSON).
		contentType(ContentType.JSON).
		header("Content-Type", "application/json").body("{\r\n"
				+ "	\"title\": \"foo\",\r\n"
				+ "    \"body\": \"bar\",\r\n"
				+ "    \"userId\": 10\r\n"
				+ "}").when().
		put("posts").then().assertThat().statusCode(404);

	}


}
