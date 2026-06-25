package tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestAssuredGet {
    @Test
    public void getuserDetails()
    {
         int status_code=given().when().get("https://dummyjson.com/users/1").then().extract().statusCode();
        System.out.println("status code : " +status_code);
    }
    @Test
    public void getrespbody()
    {
        given().when().get("https://dummyjson.com/products/1").then().log().body();
    }
}
