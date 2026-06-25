package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class stepdefinition {
@Given("user launches browser")
public void launch_browser()
{
    System.out.println("Browser launched");
  //  throw new io.cucumber.java.PendingException();
}
@When("user opens login page")
public void open_login_page()
{
    System.out.println("Opening login page");
 //   throw new io.cucumber.java.PendingException();
}
@When("user enter username and password")
public void enter_userCredential()
{
    System.out.println("Entering credentials");
  //  throw new io.cucumber.java.PendingException();
}
@Then("user should see homepage")
public void login_successful()
{
    System.out.println("Logged in successfully");
   // throw new io.cucumber.java.PendingException();
}
}
