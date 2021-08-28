package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.converter.CreateOrUpdateEmployeeDtoConverter;
import jp.co.axa.apidemo.converter.EmployeeDtoConverter;
import jp.co.axa.apidemo.dto.CreateOrUpdateEmployeeRequestDto;
import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.ApiBusinessException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * EmployeeServiceImpl
 * Class that implements the EmployeeService class.
 * It defines the different service for EmployeeController.
 * We can retrieve an employee or a list of employee.
 * We can save, update or delete an employee.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeDtoConverter employeeDtoConverter;

    @Autowired
    private CreateOrUpdateEmployeeDtoConverter createOrUpdateEmployeeDtoConverter;

    private Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    public List<EmployeeDto> retrieveEmployees() {
        List<Employee> employees = null;
        try {
            employees = employeeRepository.findAll();
        } catch (Throwable t) {
            logger.error("Error occurred while fetching Employee records.");
            throw new ApiBusinessException("Fetching Employees data failed!", HttpStatus.INTERNAL_SERVER_ERROR, t.getLocalizedMessage());
        }
        return employeeDtoConverter.entityToDtoList(employees);
    }

    @Override
    @Cacheable(cacheNames = "employees", key = "#employeeId")
    public EmployeeDto getEmployee(Long employeeId) {
        Optional<Employee> optEmp = null;
        try {
            optEmp = employeeRepository.findById(employeeId);

            return employeeDtoConverter.entityToDto(
                    optEmp.orElseThrow(() -> new ApiBusinessException("Data not found for Employee with ID " + employeeId + " !",
                            HttpStatus.NOT_FOUND))
            );
        } catch (Throwable t) {
            logger.error("Error occurred while fetching data for Employee ID: " + employeeId + " !");
            throw new ApiBusinessException("Fetching Employee data with ID: " + employeeId + " failed!", HttpStatus.INTERNAL_SERVER_ERROR, t.getLocalizedMessage());
        }
    }

    @Override
    public void saveEmployee(CreateOrUpdateEmployeeRequestDto employee) {
        Employee emp = null;
        try {
            emp = createOrUpdateEmployeeDtoConverter.dtoToEntity(employee);
            employeeRepository.save(emp);
        } catch (Throwable t) {
            logger.error("Error occurred while saving Employee record.");
            throw new ApiBusinessException("Unable to save employee with ID " + emp.getId(), HttpStatus.INTERNAL_SERVER_ERROR, t.getLocalizedMessage());
        }
    }

    @Override
    @CacheEvict(cacheNames = "employees", key = "#employeeId")
    public void deleteEmployee(Long employeeId) {
        EmployeeDto emp;
        try {
            emp = getEmployee(employeeId);
            if (emp == null) {
                throw new ApiBusinessException("Unable to delete record. Data not found for Employee with ID " + emp.getId(), HttpStatus.NOT_FOUND);
            }
            employeeRepository.deleteById(employeeId);
        } catch (Throwable t) {
            logger.error("Error occurred while deleting Employee record.");
            throw new ApiBusinessException("Unable to delete employee with ID " + employeeId, HttpStatus.INTERNAL_SERVER_ERROR, t.getLocalizedMessage());
        }
    }

    @Override
    @CachePut(cacheNames = "employees", key = "#employeeId")
    public void updateEmployee(Long employeeId, CreateOrUpdateEmployeeRequestDto employee) {
        EmployeeDto emp;
        try {
            emp = getEmployee(employeeId);
            if (emp == null) {
                throw new ApiBusinessException("Unable to delete record. Data not found for Employee with ID " + employeeId, HttpStatus.NOT_FOUND);
            }
            Employee emply = createOrUpdateEmployeeDtoConverter.dtoToEntity(employee);
            emply.setId(employeeId);
            employeeRepository.save(emply);
        } catch (Throwable t) {
            logger.error("Error occurred while deleting Employee record.");
            throw new ApiBusinessException("Unable to update employee with ID " + employeeId, HttpStatus.INTERNAL_SERVER_ERROR, t.getLocalizedMessage());
        }

    }
}