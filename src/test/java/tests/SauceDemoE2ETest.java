package tests;
import Utils.ConfigReader;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import java.util.List;
import java.util.Map;

public class SauceDemoE2ETest extends BaseTest {
  //  @Test(groups = "smoke")
    @Test(dataProvider = "validUser")    public void login(String user,String pwd) {

        getDriver().get(ConfigReader.get("baseurl"));
        LoginPage loginObj = new LoginPage();
        loginObj.login(user, pwd);
        log.info("Navigating to login page");
        InventoryPage inventory = loginObj.navigateToInventory();
        Assert.assertEquals(inventory.getHomeTitle(), "Products");
        loginObj.logout();
    }

    @Test(dataProvider = "invalidUser",enabled = false)
    public void invalidLogin(String user,String pwd)
    {
        getDriver().get(ConfigReader.get("baseurl"));
        LoginPage loginObj = new LoginPage();
        String errorMsg= loginObj.getErrorMsg(user,pwd);
        log.error("Error captured due to Invalid user credentials entered " + errorMsg);
        Assert.assertEquals("Epic sadface: Username and password do not match any user in this service",errorMsg);
    }

    //@Test(dependsOnMethods = "loginTest", groups = "regression")
    @Test(dataProvider = "validUser")
    public void addToCartTest(String user,String pwd) {
        getDriver().get(ConfigReader.get("baseurl"));
        LoginPage loginObj = new LoginPage();
        loginObj.login(user, pwd);
        InventoryPage inventory = loginObj.navigateToInventory();
        inventory.addToCart("Sauce Labs Backpack");
        //inventory.addToCart("Sauce Labs Bolt T-Shirt");        inventory.addToCart("Sauce Labs Bike Light");
        int count = inventory.cartCount();
        Assert.assertEquals(count, 1);
        log.info("Count of Items added in cart is: " + count);
        loginObj.logout();
    }
    @Test(dataProvider = "validUser")
    public void removeFromCartTest(String user, String pwd) {
        getDriver().get(ConfigReader.get("baseurl"));
        LoginPage loginObj = new LoginPage();
        loginObj.login(user, pwd);
        InventoryPage inventory = loginObj.navigateToInventory();
        String prod = "Sauce Labs Backpack";
        String prod1 = "Sauce Labs Bolt T-Shirt";
        inventory.addToCart(prod);
       // inventory.addToCart(prod1);
        int count = inventory.cartCount();
        int count_after_removal =0;
        if(inventory.removeProduct(prod))
        {
            log.info("Product " + prod + " has been removed from the cart");
            inventory.pageRefresh();
        }
        else {
            log.info("remove cart button disable for the product");
        }
        count_after_removal = inventory.cartCount();
        Assert.assertEquals(count-1,count_after_removal);
        log.info("Items count in cart currently"+ count_after_removal );
        loginObj.logout();

    }
    @Test(dataProvider = "validUser")
    public void removeFromCartPage(String user, String pwd)
    {
        getDriver().get(ConfigReader.get("baseurl"));
        LoginPage loginObj = new LoginPage();
        loginObj.login(user, pwd);
        InventoryPage inventory = loginObj.navigateToInventory();
        String prod = "Sauce Labs Backpack";
        String prod1 = "Sauce Labs Bolt T-Shirt";
        inventory.addToCart(prod);
        inventory.addToCart(prod1);
        CartPage cartObj = inventory.goToCart();
        cartObj.removeFromCart(prod);
        int count =inventory.cartCount();
        log.info("Items count in cart currently"+ count );
        if(count>0)
        {
            List<Map<String,String>> cartItems = cartObj.getProducts();
            Assert.assertTrue(cartItems.stream().noneMatch(item -> prod.equals(item.get("name"))),"Product is still present");
        }
        else {
            log.info("No product in the cart, Cart is empty");
            Assert.assertEquals(count,0,"Cart should be empty, but it is not!");
        }
        log.info("cart count after removal"+cartObj.cartCount());
        loginObj.logout();

    }

    @Test
    public void verifyCartTest() {
        getDriver().get(ConfigReader.get("baseurl"));
        LoginPage loginObj = new LoginPage();
        loginObj.login("standard_user", "secret_sauce");
        InventoryPage inventory = loginObj.navigateToInventory();
        inventory.addToCart("Sauce Labs Backpack");
        inventory.addToCart("Sauce Labs Bolt T-Shirt");
        List<Map<String, String>> addedList = inventory.getSelectedProducts();
        CartPage cartObj = inventory.goToCart();
        List<Map<String, String>> cartItems = cartObj.getProducts();
        Assert.assertEquals(addedList, cartItems, "mismatch in inventory vs cart page items");
        log.info("Cart items verified");
        loginObj.logout();
    }

    @Test(dataProvider = "validCheckoutUser")
    public void checkoutTest(String first,String last,String post) {
        getDriver().get(ConfigReader.get("baseurl"));
        LoginPage loginObj = new LoginPage();
        loginObj.login("standard_user", "secret_sauce");
        InventoryPage inventory = loginObj.navigateToInventory();
        inventory.addToCart("Sauce Labs Backpack");
        inventory.addToCart("Sauce Labs Bolt T-Shirt");
        CartPage cartObj = inventory.goToCart();
        CheckoutInfoPage checkout = cartObj.checkout();
        checkout.getUserInfo(first, last, post);
        Assert.assertTrue(checkout.isPreviewpage());
    }

    @Test(dataProvider = "validCheckoutUser")
    public void checkoutPreviewTest(String first,String last,String post) {
        getDriver().get(ConfigReader.get("baseurl"));
        LoginPage loginObj = new LoginPage();
        loginObj.login("standard_user", "secret_sauce");
        InventoryPage inventory = loginObj.navigateToInventory();
        inventory.addToCart("Sauce Labs Backpack");
        inventory.addToCart("Sauce Labs Bolt T-Shirt");
        CartPage cartObj = inventory.goToCart();
        CheckoutInfoPage checkout = cartObj.checkout();
        checkout.getUserInfo(first, last, post);
        CheckoutPreview preview = checkout.returnPreviewpage();
        List<Map<String, String>> previewItems = preview.getPreviewItems();
        log.info("Verifying cart items");
        Assert.assertEquals(cartObj.getProducts(), previewItems, "Mismatch in cart vs preview page items list");
        Double itemTotalUI = preview.getTotalFromUI();
        Double itemTotalManual = preview.getCalculatedItemTotal();
        log.info("Verifying checkout totals");
        Assert.assertEquals(preview.getCalculatedItemTotal(), preview.getTotalFromUI(), "Mismatch in item total in checkout preview page");
        Assert.assertEquals(preview.totalOfUI(), preview.totalOfCalculated(), "Mismatch in Total Calculation");
        loginObj.logout();
    }

    @Test(dataProvider = "validCheckoutUser")
    public void placeOrderTest(String first,String last,String post) {
        getDriver().get(ConfigReader.get("baseurl"));
        LoginPage loginObj = new LoginPage();
        loginObj.login("standard_user", "secret_sauce");
        InventoryPage inventory = loginObj.navigateToInventory();
        inventory.addToCart("Sauce Labs Backpack");
        CartPage cartObj = inventory.goToCart();
        CheckoutInfoPage checkout = cartObj.checkout();
        checkout.getUserInfo(first, last, post);
        CheckoutPreview preview = checkout.returnPreviewpage();
        OrderCompletionPage order = preview.placeOrder();
        order.getCompletionText();
        Assert.assertTrue(order.getCompletionText().contains("Your order has been dispatched"));
        order.getthankYouHeader();
        Assert.assertEquals(order.getthankYouHeader(), "Thank you for your order!");
        LoginPage logoutObj = order.logout();
        Assert.assertEquals(logoutObj.getLoginLogo(), "Swag Labs");
    }

    @Test
    public void logoutTest() {
        OrderCompletionPage order = new OrderCompletionPage();
        LoginPage logoutObj = order.logout();
        Assert.assertEquals(logoutObj.getLoginPageTitle(), "Swag Labs");
    }
    @Test(dataProvider = "invalidUser")
    public void demoFailure(String user,String pwd)
    {
        getDriver().get(ConfigReader.get("baseurl"));
        LoginPage loginObj = new LoginPage();
        String errorMsg= loginObj.getErrorMsg(user,pwd);
        log.error("Error captured due to Invalid user credentials entered " + errorMsg);
        Assert.assertEquals("logged in successfully",errorMsg);
    }

    @DataProvider(name = "validUser", parallel = true)
    public Object[][] validUser() {
        return new Object[][]{{"standard_user", "secret_sauce"}
              //  {"problem_user","secret_sauce"},{"error_user","secret_sauce"},
               //    {"visual_user","secret_sauce"}
        };
    }
    @DataProvider(name = "invalidUser", parallel = false)
    public Object[][] invalidUser() {
        return new Object[][]{
                {"standard", "secret"},{"locked_out_user","dev6292"},{"standad_user","secret_suce"},
                {"standard_user","htrjyj"},{"werwe45","secret_sauce"}
        };
    }
    @DataProvider(name = "validCheckoutUser", parallel = true)
    public Object[][] validData() {
        return new Object[][]{
                {"san", "dev", "2933"}
        };
    }
    @DataProvider(name = "invalidCheckoutUser", parallel = true)
    public Object[][] invalidData() {
        return new Object[][]{
                {"", "dev", "2933"},{"san","","4546"},{"san","dev",""},{"","",""}
        };

    }
    @DataProvider(name = "productList", parallel = true)
    public Object[][] productList() {
        return new Object[][]{{"Sauce Labs Backpack"},{"Sauce Labs Bolt T-Shirt"}};

    }

}


