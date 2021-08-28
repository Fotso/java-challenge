package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.CreateOrUpdateEmployeeRequestDto;
import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

/**
 * EmployeeService
 * Interface for EmployeeService class.
 * It provides the CRUD body method for retrieving Employee data.
 */
public interface EmployeeService {

    public List<EmployeeDto> retrieveEmployees();

    public EmployeeDto getEmployee(Long employeeId);

    public void saveEmployee(CreateOrUpdateEmployeeRequestDto employee);

    public void deleteEmployee(Long employeeId);

    public void updateEmployee(Long employeeId, CreateOrUpdateEmployeeRequestDto employee);
}