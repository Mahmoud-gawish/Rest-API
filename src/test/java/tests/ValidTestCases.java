package tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ValidTestCases {
	
	private static RequestSpecification requestSpec;
	private static ResponseSpecification responseSpec;

	@BeforeSuite
	public static void createRequestSpecification() {

		requestSpec = new RequestSpecBuilder().
				setBaseUri("https://jsonplaceholder.typicode.com").build();
		responseSpec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
	}
	@Test(priority = 1)
	public void verifyGetAll() {
		given().spec(requestSpec).
		get("posts").then().assertThat().statusCode(200);

	}

	@Test(priority = 2) 
	public void verifyInstancePost() {

		given().spec(requestSpec).when().get("posts/1").
		then().spec(responseSpec).and().assertThat().body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));
	}
	@Test(priority = 3)
	public void verifyAddPost() {
		given().spec(requestSpec).
		accept(ContentType.JSON).
		contentType(ContentType.JSON).
		header("Content-Type", "application/json").body("{\r\n"
				+ "	\"title\": \"foo\",\r\n"
				+ "    \"body\": \"bar\",\r\n"
				+ "    \"userId\": 1\r\n"
				+ "}").when().
		post("posts").then().assertThat().body("body", equalTo("bar"));

	}


}
