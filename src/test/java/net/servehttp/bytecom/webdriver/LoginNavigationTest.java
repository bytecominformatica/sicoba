package net.servehttp.bytecom.webdriver;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 
 * @author Felipe Martins
 *
 */
public class LoginNavigationTest {

  private static WebDriver driver;
  
  @BeforeClass
  public static void setUpTest() {
    System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver/chromedriver");
    driver = new ChromeDriver();
    driver.get("http://localhost:8080/sicoba");
  }
  
  @Test
  public void loginTest() {
    WebElement element = driver.findElement(By.id("form1:usuario"));
    element.sendKeys("selenium");
    
    element = driver.findElement(By.id("form1:senha"));
    element.sendKeys("teste");
    
    driver.findElement(By.id("form1:btLogin")).click();
  }
  
  @AfterClass
  public static void tearDownTest() {
    driver.quit();
  }

}
