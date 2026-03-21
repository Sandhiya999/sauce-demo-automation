package tests;
import Utils.ConfigReader;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import java.util.List;
import java.util.Map;
@Test(dataProvider = "invalidCheckoutUser")
public class SauceE2eDemo extends BaseTest{
    public void E2eFLow(String user,String pwd,String first,String last,String post)
    {
       getDriver().get(ConfigReader.get("baseurl"));
        LoginPage loginObj = new LoginPage();
        log.info("Navigating to login page");
        loginObj.login(user, pwd);
        InventoryPage inventory = loginObj.navigateToInventory();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory") && inventory.isInventoryPageLoaded());
        inventory.addToCart("Sauce Labs Backpack");
        int count = inventory.cartCount();
        Assert.assertEquals(count, 1);
        log.info("Count of Items added in cart is: " + count);
        List<Map<String, String>> addedList = inventory.getSelectedProducts();
        CartPage cartObj = inventory.goToCart();
        List<Map<String, String>> cartItems = cartObj.getProducts();
        Assert.assertEquals(addedList, cartItems, "mismatch in inventory vs cart page items");
        CheckoutInfoPage checkout = cartObj.checkout();
        checkout.getUserInfo(first,last,post);
       // Assert.assertEquals(checkout.getCheckOutErr(),err);
        Assert.assertTrue(checkout.isPreviewpage());
        CheckoutPreview preview = checkout.returnPreviewpage();
        List<Map<String, String>> previewItems = preview.getPreviewItems();
        log.info("Verifying cart items");
        Assert.assertEquals(cartObj.getProducts(), previewItems, "Mismatch in cart vs preview page items list");
        log.info("Verifying checkout totals");
        Assert.assertEquals(preview.getCalculatedItemTotal(), preview.getTotalFromUI(), "Mismatch in item total in checkout preview page");
        Assert.assertEquals(preview.totalOfUI(), preview.totalOfCalculated(), "Mismatch in Total Calculation");
        OrderCompletionPage order = preview.placeOrder();
        order.getCompletionText();
        Assert.assertTrue(order.getCompletionText().contains("Your order has been dispatched"));
        order.getthankYouHeader();
        Assert.assertEquals(order.getthankYouHeader(), "Thank you for your order!");
        LoginPage logoutObj = order.logout();
        Assert.assertEquals(logoutObj.getLoginLogo(), "Swag Labs");
    }
    @DataProvider(name = "validUser", parallel = true)
    public Object[][] validUser() {
        return new Object[][]{{"standard_user", "secret_sauce"}
                // {"san","dev","6292","Error: 1Last Name is required"}//{"san","dev","","Error: Postal Code is required"},
                //   {"san","dev","","Error: Postal Code is required"}
        };
    }
    @DataProvider(name = "invalidUser", parallel = true)
    public Object[][] invalidUser() {
        return new Object[][]{{"standard", "secret"},
                 {"1344","fred"}
        };
    }
    @DataProvider(name = "validCheckoutUser", parallel = true)
    public Object[][] validData() {
        return new Object[][]{{"standard_user", "secret_sauce","san", "dev", "5345"}
                // {"san","dev","6292","Error: 1Last Name is required"}//{"san","dev","","Error: Postal Code is required"},
                //   {"san","dev","34754","Error: Postal Code is required"}
        };
    }
    @DataProvider(name = "invalidCheckoutUser", parallel = true)
    public Object[][] invalidData() {
        return new Object[][]{{"standard_user", "secret_sace","san", "dev", "464"}
                // {"san","","6292","Error: 1Last Name is required"}//{"san","dev","","Error: Postal Code is required"},
                //   {"san","dev","","Error: Postal Code is required"}
        };
    }
}
