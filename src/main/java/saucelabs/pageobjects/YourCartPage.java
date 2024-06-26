package saucelabs.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class YourCartPage {
    WebDriver driver;

    public YourCartPage(WebDriver driver){
    this.driver= driver;
    PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//div[@class=\"cart_item\"]")
    List<WebElement> cartItems;

    @FindBy(id="checkout")
    WebElement checkOutButton;

    public void verifyItemsInCart(String prodName)
    {
        PageFactory.initElements(driver, this);
    boolean x = cartItems.stream().
            anyMatch(cartItem -> cartItem.findElement(By.xpath(".//div[@class=\"inventory_item_name\"]"))
                    .getText()
                    .contains(prodName));

     if(x==true)
     {
         System.out.println(prodName+" is present in cart");
     }
     else {
         System.out.println(prodName + " is not present in cart");
     }
    }

    public void proceedToCheckout()
    {
        checkOutButton.click();
    }
    }