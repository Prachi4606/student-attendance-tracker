
package com.attendance.student_attendance_tracker;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AttendanceSeleniumTest {

    @Test
    void applicationOpensInChrome() {
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("http://localhost:8081");

            String title = driver.getTitle();

            System.out.println("Page title: " + title);

            assertTrue(
                driver.getCurrentUrl().contains("localhost:8081"),
                "Attendance application should open in Chrome"
            );
        } finally {
            driver.quit();
        }
    }
}
