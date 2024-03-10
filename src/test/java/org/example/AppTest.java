package org.example;

import io.appium.java_client.android.AndroidDriver;
import org.example.driver.MyDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author nikolaus.wijaya on 10/03/2024
 * @project SeleniumGrid4Appium
 */
public class AppTest {

  private AndroidDriver androidDriver = null;
  static Properties props = null;
  private MyDriver myDriver;

  public static void loadProperties() {
    String resourceName = "junit-platform.properties"; // could also be a constant
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    props = new Properties();
    try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
      props.load(resourceStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  @BeforeAll
  public static void start() {
    System.out.println("=======Starting Automation Android========");
    loadProperties();
  }

  @BeforeEach
  public void setup() {
    System.out.println("=======Setting up driver and devices========");
    myDriver = new MyDriver();
  }

  @Test
  public void testAndroid1() throws InterruptedException {
    androidDriver = myDriver.setupAndroid(props.getProperty("android.appPackage"),
        props.getProperty("android.appActivity"), props.getProperty("seleium.grid.url"));
    Thread.sleep(8000);
    String methodName = Thread.currentThread()
        .getStackTrace()[1]
        .getMethodName();
    System.out.println("********Execution of " + methodName + " has been started********");
    androidDriver.findElement(By.xpath("(//android.widget.ImageView[@resource-id=\"com.google.android.youtube:id/image\"])[4]")).click();
    Thread.sleep(2000);
    assertThat("channel youtube not found",
        androidDriver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.google.android.youtube:id/title\"]"))
            .getText(), equalTo("Don’t miss new videos"));
    System.out.println("********Execution of " + methodName + " has ended********");
  }

  @AfterEach
  public void tearDown() {
    System.out.println("Quitting the apps has started");
    androidDriver.quit();
    System.out.println("Quitting the apps has ended");
  }

  @AfterAll
  public static void end() {
    System.out.println("Tests ended");
  }
}
