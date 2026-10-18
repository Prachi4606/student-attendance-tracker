
package com.attendance.student_attendance_tracker;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AttendanceSeleniumTest {

    @LocalServerPort
    private int port;

    @Test
    void applicationOpensInChrome() {
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("http://localhost:" + port);

            System.out.println("Page title: " + driver.getTitle());

            assertTrue(
                driver.getCurrentUrl().contains("localhost:" + port),
                "Attendance application should open in Chrome"
            );
        } finally {
            driver.quit();
        }
    }
}
