package saucelabs.testcomponents;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import saucelabs.common.SiteHeader;
import saucelabs.pageobjects.*;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class happyPath extends BaseTest{

    @Test(dataProvider = "getData")
      public void demoTest(HashMap<String,String> input) throws Exception {

        //Items to be ordered
        List<String> desiredItems = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Fleece Jacket");

        //Open app, login

        loginPage.loginToApp(input.get("username"), input.get("password"));
        //loginPage.loginToApp("siva", "password");

        //add desired items in cart
        ProductsPage productsPageObj = new ProductsPage(driver);
        for (String desiredItem: desiredItems)
        {
            productsPageObj.selectProductByName(desiredItem);
            productsPageObj.addProductToCart(desiredItem);
        }

        //go to cart section
        SiteHeader siteHeader = new SiteHeader(driver);
        siteHeader.clickShoppingCartLink();

        //verify the information in cartpage
        YourCartPage yourCartPageObj = new YourCartPage(driver);
        for(String desiredItem: desiredItems)
        {
            //System.out.println("driver class "+desiredItem);
            yourCartPageObj.verifyItemsInCart(desiredItem);
        }
        yourCartPageObj.proceedToCheckout();

        //provide your personal info to proceed to order
        YourInformationPage yourInformationPageObj = new YourInformationPage(driver);
        yourInformationPageObj.providePersonalDetails(input.get("firstName"), input.get("lastName"), input.get("postalCode"));
        yourInformationPageObj.proceedFurther();

        //finally verify all your details
        CheckoutOverviewPage checkoutOverviewPageObj = new CheckoutOverviewPage(driver);
        checkoutOverviewPageObj.valueOfCartItemsBeforeTax();
        checkoutOverviewPageObj.valueOfCartItemsAfterTax();
        checkoutOverviewPageObj.proceedToFinish();

        //compete checkout and return to products page
        CheckoutCompletePage checkoutCompletePageObj = new CheckoutCompletePage(driver);
        checkoutCompletePageObj.verifyCompletionMessage(input.get("thanksText"));
        checkoutCompletePageObj.proceedBackToProducts();
    }

    @Test

    @DataProvider
            public Object[][] getData() throws IOException {
      String dataFilePath = System.getProperty("user.dir")+"/src/test/java/saucelabs/data/happypath/happypath.json";
      List<HashMap<String,String>> data = getDataToMap(dataFilePath);
      return new Object[][] {{data.get(0)}};
    }


}
