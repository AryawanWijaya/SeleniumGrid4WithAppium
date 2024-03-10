package org.example.driver;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author nikolaus.wijaya on 05/03/2024
 * @project SeleniumGrid4Appium
 */
public class MyDriver {
  public AndroidDriver setupAndroid(String appPackage, String appActivity, String urlAppium) {
    System.out.println("Setting up the android devices");
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("platformName", "android");
    capabilities.setCapability("appium:automationName", "UiAutomator2");
    capabilities.setCapability("appium:deviceName", "emulator-5554");
    capabilities.setCapability("appium:forceAppLaunch", true); // for launch apps
    // capabilities.setCapability("app",apkPath); // for instalation apps
    // basically we can open apps that already installed by use this caps
    // we can found apps package and app activity from adb shell
    capabilities.setCapability("appPackage", appPackage);
    capabilities.setCapability("appActivity", appActivity);
    capabilities.setCapability("noReset", true); // apps nota always start from first open apps
    capabilities.setCapability("shouldTerminateApp", true); // for close apps when already done
    try {
      URL url = new URL(urlAppium);
      return new AndroidDriver(url, capabilities);
    } catch (MalformedURLException e) {
      System.out.println("Invalid grid URL");
      return null;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }
}
