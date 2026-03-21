package pages;

import org.openqa.selenium.By;



public class LoginPage extends BasePage {
    public LoginPage()
    {
        super();
    }
    private By userID = By.id("user-name");
    private By pwd = By.id("password");
    private By login_Btn = By.cssSelector("input#login-button");
    private By error_msg = By.cssSelector("h3[data-test='error']");
    private By loginTitle = By.cssSelector("[data-test='title']");
    private By login_logo = By.cssSelector(".login_logo");

    public void login(String name,String pass)
    {
        type(userID,name);
        type(pwd,pass);
        click(login_Btn);
    }

    public String getErrorMsg(String name,String pass)    {
        type(userID,name);
        type(pwd,pass);
        click(login_Btn);
        return getTextValue(error_msg);    }
    public InventoryPage navigateToInventory()
    {
        return new InventoryPage();
    }
    public String getLoginPageTitle()  {
        return getTextValue(loginTitle);
    }
    public String getLoginLogo()
    {
        return  getTextValue(login_logo);
    }
}
