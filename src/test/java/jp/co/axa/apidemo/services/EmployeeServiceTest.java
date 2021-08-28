package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.ApiDemoApplication;
import jp.co.axa.apidemo.converter.CreateOrUpdateEmployeeDtoConverter;
import jp.co.axa.apidemo.converter.EmployeeDtoConverter;
import jp.co.axa.apidemo.dto.CreateOrUpdateEmployeeRequestDto;
import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.ApiBusinessException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * EmployeeServiceTest
 * Test the Employee Service class.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApiDemoApplication.class)
public class EmployeeServiceTest {

    @Autowired
    EmployeeServiceImpl employeeService;

    @Mock
    EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void retrieve_employeeOne_test() {
        Employee employee = new Employee(1L, "Test", 8000000, "Test");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(1L);
    }

    @Test
    public void retrieve_employeeError_test() {
        Long empId = 1L;
        when(employeeRepository.findById(empId)).thenReturn(Optional.empty());
        try {
            EmployeeDto employee = employeeService.getEmployee(empId);
        }catch(ApiBusinessException e) {
            assertThat(e.getErrorMessage()).isEqualTo("Fetching Employee data with ID: "+ empId +" failed!");
        }
    }

    @Test
    public void retrieve_employeeList_success() {
        EmployeeDtoConverter converter = new EmployeeDtoConverter();
        List<EmployeeDto> list = createEmployeeList();
        List<Employee> employeeList = converter.dtoToEntityList(list);
        when(employeeRepository.findAll()).thenReturn(employeeList);

        List<Employee> allEmployees = converter.dtoToEntityList(employeeService.retrieveEmployees());

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(employeeList.size());
    }

    @Test
    public void save_employeesTest_success() {
        Employee employee = new Employee(1L, "Test", 8000000, "Test");

        when(employeeRepository.save(employee)).thenReturn(employee);
        CreateOrUpdateEmployeeDtoConverter converter = new CreateOrUpdateEmployeeDtoConverter();
        CreateOrUpdateEmployeeRequestDto tmp = converter.entityToDto(employee);
        try {
            employeeService.saveEmployee(tmp);
        }catch (ApiBusinessException e) {
            //if an error is raised, it means our test have failed.
            Assert.assertTrue(false);
        }
    }

    @Test
    public void delete_employeesTest_success() {
        Long employeeId = 4L;
        try {
            employeeService.deleteEmployee(employeeId);
        }catch (ApiBusinessException e) {
            //if an error is raised, it means our test have failed.
            Assert.assertTrue(false);
        }
    }

    @Test
    public void saveEmployeesTest_success() {
        Employee employee = new Employee(1L, "Test", 8000000, "Test");

        when(employeeRepository.save(employee)).thenReturn(employee);
        CreateOrUpdateEmployeeDtoConverter converter = new CreateOrUpdateEmployeeDtoConverter();
        CreateOrUpdateEmployeeRequestDto tmp = converter.entityToDto(employee);
        employeeService.saveEmployee(tmp);
    }

    public List<EmployeeDto> createEmployeeList() {
        List<EmployeeDto> list = new ArrayList<>();

        EmployeeDto emp1 = new EmployeeDto(1L, "Test1", 8000000, "Test1");
        EmployeeDto emp2 = new EmployeeDto(2L, "Test2", 9000000, "Test2");
        EmployeeDto emp3 = new EmployeeDto(3L, "Test3", 10000000, "Test3");

        list.add(emp1);
        list.add(emp2);
        list.add(emp3);

        return list;
    }
}
