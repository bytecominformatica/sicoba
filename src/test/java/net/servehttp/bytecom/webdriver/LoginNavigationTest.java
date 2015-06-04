package net.servehttp.bytecom.webdriver;

import static org.junit.Assert.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * 
 * @author Felipe Martins
 *
 */
public class LoginNavigationTest {

  private static WebDriver driver;
  
  @BeforeClass
  public static void setUpTest() {
    driver = new FirefoxDriver();
    driver.get("http://localhost:8080/sicoba");
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
  }
  
  @Test
  public void loginTest() {
    WebElement element = driver.findElement(By.id("form1:usuario"));
    element.sendKeys("selenium");
    
    element = driver.findElement(By.id("form1:senha"));
    element.sendKeys("teste");
    
    driver.findElement(By.id("form1:btLogin")).click();
    
    element = null;
    
    try {
      element = driver.findElement(By.xpath("//a[@href='#mensalidadesEmAtraso']")); //Se passar por aqui conseguiu logar :)
    } catch (Exception e) {
      // TODO: handle exception
    }
    assertNotNull(element);
  }
  
  @AfterClass
  public static void tearDownTest() {
    driver.quit();
  }

}
