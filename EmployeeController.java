package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping("/employees")
    public String createNewEmployee(@RequestBody Employee employee) {
        employeeRepository.save(employee);
        return "Employee Created in database";
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> empList = new ArrayList<>();
        employeeRepository.findAll().forEach(empList::add);
        return new ResponseEntity<>(empList, HttpStatus.OK);
    }

    @GetMapping("/employees/{empid}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long empid) {
        Optional<Employee> emp = employeeRepository.findById(empid);
        if (emp.isPresent()) {
            return new ResponseEntity<>(emp.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/employees/{empid}")
    public String updateEmployeeById(@PathVariable long empid, @RequestBody Employee employee) {
        Optional<Employee> emp = employeeRepository.findById(empid);
        if (emp.isPresent()) {
            Employee existEmployee = emp.get();
            existEmployee.setEmp_age(employee.getEmp_age());
            existEmployee.setEmp_city(employee.getEmp_city());
            existEmployee.setEmp_name(employee.getEmp_name());
            existEmployee.setEmp_salary(employee.getEmp_salary());
            employeeRepository.save(existEmployee);
            return "Employee Details against Id " + empid + " updated";
        } else {
            return "Employee Details do not exist for empid " + empid;
        }
    }

    @DeleteMapping("/employees/{empid}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable long empid) {
        Optional<Employee> emp = employeeRepository.findById(empid);
        if (emp.isPresent()) {
            employeeRepository.deleteById(empid);
            return new ResponseEntity<>("Employee Deleted Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/employees")
    public String deleteAllEmployees() {
        employeeRepository.deleteAll();
        return "All Employees Deleted Successfully";
    }
}
