package testing;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import application.Login;
import utilities.Driver;
import utilities.Framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelLoginTestWithDataProvider {

    @BeforeTest
    public void setUp() {
        Framework.resetDriver();
    }

    @Test(dataProvider = "loginData", priority = 1)
    public void loginTest(String username, String password) {
        try {
            Login login = new Login();
            login.LoadWebsite();
            Thread.sleep(3000);
            String expectedTitle = "Shop – ToolsQA Demo Site";
            String originalTitle = Driver.getWebInstance().getTitle();
            Assert.assertEquals(originalTitle, expectedTitle);
            login.ClickOnDismiss();
            login.ClickOnMyAccount();
            Thread.sleep(5000);
            // Driver.getWebInstance().findElement(By.id("login-email")).sendKeys(username);
            // Driver.getWebInstance().findElement(By.id("login-password")).sendKeys(password);
            // Driver.getWebInstance().findElement(By.xpath("//button[contains(text(),'Sign in')]")).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Add verification logic here

        // Optional: Add a delay/wait before moving to the next set of credentials
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws IOException, EncryptedDocumentException, InvalidFormatException {
        FileInputStream file = new FileInputStream(new File("C:\\Users\\tumelog\\Downloads\\Capitec-20240305T104734Z-001\\Capitec\\src\\main\\java\\utilities\\sampledoc.xlsx"));
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);

        Object[][] data = new Object[sheet.getLastRowNum()][2];

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            data[i - 1][0] = row.getCell(0).getStringCellValue();
            data[i - 1][1] = row.getCell(1).getStringCellValue();
        }

        file.close();
        return data;
    }
}