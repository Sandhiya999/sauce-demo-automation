package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestAssuredPost {
    @Test
    public void getToken()
    {
        String token = given().contentType("application/json")
                .body("{\"username\":\"emilys\",\"password\":\"emilyspass\"}")
                .when().post("https://dummyjson.com/auth/login")
                .then().statusCode(200).extract().path("accessToken");
        System.out.println("token : "+token);
        given().header("Authorization", "Bearer " + token)
                .when().get("https://dummyjson.com/users" +
                        "")
                .then().statusCode(200);
    }
}
