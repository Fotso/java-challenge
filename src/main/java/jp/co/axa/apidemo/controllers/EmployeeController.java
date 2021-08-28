package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.dto.CreateOrUpdateEmployeeRequestDto;
import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.exception.ApiBusinessException;
import jp.co.axa.apidemo.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * EmployeeController
 * defines the endpoints for employee.
 * A user with ROLE_ADMIN is allowed to create, update, delete and read
 * an employee. A user with ROLE_USER is only allowed to read an employee or
 * list of employees data. Updating, deleting and creating an employee is forbidden
 * for a user with role ROLE_USER.
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        List<EmployeeDto> employees = employeeService.retrieveEmployees();
        logger.info("Employee list with " + employees.size() + " records have been fetched !");
        return new ResponseEntity<List<EmployeeDto>> (employees,HttpStatus.OK);
    }

    @GetMapping("/employees/{employeeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable(name="employeeId")Long employeeId) throws ApiBusinessException {
        EmployeeDto employee = employeeService.getEmployee(employeeId);
        logger.info("Employee with ID " + employeeId + " have been fetched !");
        return new ResponseEntity<EmployeeDto>(employee, HttpStatus.OK);
    }

    @PostMapping("/employees")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> saveEmployee(@RequestBody CreateOrUpdateEmployeeRequestDto employee) throws ApiBusinessException{
        employeeService.saveEmployee(employee);
        logger.info("Employee Saved Successfully !");
        return new ResponseEntity<String>("Employee Saved Successfully !", HttpStatus.OK);
    }

    @DeleteMapping("/employees/{employeeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteEmployee(@PathVariable(name="employeeId")Long employeeId) throws ApiBusinessException{
        employeeService.deleteEmployee(employeeId);
        logger.info("Employee Deleted Successfully !");
        return new ResponseEntity<String>("Employee Deleted Successfully !", HttpStatus.OK);
    }

    @PutMapping("/employees/{employeeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateEmployee(@RequestBody CreateOrUpdateEmployeeRequestDto employee,
                               @PathVariable(name="employeeId")Long employeeId) throws ApiBusinessException{
        employeeService.updateEmployee(employeeId, employee);
        logger.info("Employee Updated Successfully !");
        return new ResponseEntity<String>("Employee Updated Successfully !", HttpStatus.OK);
    }

}
