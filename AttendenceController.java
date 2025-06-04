package com.example.attendance.controller;
import com.example.attendance.model.Attendance;
import com.example.attendance.model.Employee;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
 
import java.time.LocalDate;
import java.util.List;
 
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
 
    @Autowired
    private AttendanceRepository attendanceRepo;
 
    @Autowired
    private EmployeeRepository employeeRepo;
 
    @PostMapping("/mark/{employeeId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public Attendance markAttendance(@PathVariable Long employeeId, @RequestParam String status) {
        Employee emp = employeeRepo.findById(employeeId).orElseThrow();
        Attendance att = new Attendance();
        att.setEmployee(emp);
        att.setDate(LocalDate.now());
        att.setStatus(status);
        return attendanceRepo.save(att);
    }
 
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasRole('MANAGER')")
    public List<Attendance> getEmployeeAttendance(@PathVariable Long employeeId) {
        Employee emp = employeeRepo.findById(employeeId).orElseThrow();
        return attendanceRepo.findByEmployee(emp);
    }
 
    @GetMapping("/department/{dept}")
    @PreAuthorize("hasRole('MANAGER')")
    public List<Attendance> getDepartmentAttendance(@PathVariable String dept) {
        return attendanceRepo.findByEmployee_Department(dept);
    }
}
Atte
