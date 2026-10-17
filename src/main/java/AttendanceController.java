
package com.attendance.studentattendancetracker;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AttendanceController {

    private final List<Student> students = new ArrayList<>();

    @GetMapping("/")
    public String home(
            @RequestParam(required = false) String date,
            Model model) {

        LocalDate selectedDate = (date == null || date.isBlank())
                ? LocalDate.now()
                : LocalDate.parse(date);

        int presentToday = 0;
        int absentToday = 0;

        for (Student student : students) {
            String status = student.getAttendance().get(selectedDate);

            if ("Present".equals(status)) {
                presentToday++;
            } else if ("Absent".equals(status)) {
                absentToday++;
            }
        }

        model.addAttribute("students", students);
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("presentToday", presentToday);
        model.addAttribute("absentToday", absentToday);
        model.addAttribute("totalStudents", students.size());

        return "index";
    }

    @PostMapping("/add")
    public String addStudent(
            @RequestParam String name,
            @RequestParam String rollNumber) {

        if (name.isBlank() || rollNumber.isBlank()) {
            return "redirect:/";
        }

        students.add(new Student(name.trim(), rollNumber.trim()));
        return "redirect:/";
    }

    @PostMapping("/mark/{index}")
    public String markAttendance(
            @PathVariable int index,
            @RequestParam String status,
            @RequestParam String date) {

        if (index >= 0 && index < students.size()
                && ("Present".equals(status)
                || "Absent".equals(status))) {

            LocalDate attendanceDate = LocalDate.parse(date);

            students.get(index).getAttendance()
                    .put(attendanceDate, status);
        }

        return "redirect:/?date=" + date;
    }

    public static class Student {

        private final String name;
        private final String rollNumber;
        private final Map<LocalDate, String> attendance = new HashMap<>();

        public Student(String name, String rollNumber) {
            this.name = name;
            this.rollNumber = rollNumber;
        }

        public String getName() {
            return name;
        }

        public String getRollNumber() {
            return rollNumber;
        }

        public Map<LocalDate, String> getAttendance() {
            return attendance;
        }

        public long getPresentDays() {
            return attendance.values().stream()
                    .filter("Present"::equals)
                    .count();
        }

        public long getAbsentDays() {
            return attendance.values().stream()
                    .filter("Absent"::equals)
                    .count();
        }

        public int getTotalMarkedDays() {
            return attendance.size();
        }

        public double getAttendancePercentage() {
            int total = getTotalMarkedDays();

            if (total == 0) {
                return 0.0;
            }

            return getPresentDays() * 100.0 / total;
        }
    }
}
