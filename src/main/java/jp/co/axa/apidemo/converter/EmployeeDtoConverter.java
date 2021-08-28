package jp.co.axa.apidemo.converter;

import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import org.springframework.stereotype.Component;

/**
 * EmployeeDtoConverter
 * Class that converts an
 * object of type EmployeeDto to an
 * object of type Employee (and vice versa).
 */
@Component
public class EmployeeDtoConverter extends AbstractConverter<Employee, EmployeeDto>{

    @Override
    public EmployeeDto entityToDto(Employee entity) {
        if (entity == null) {
            return null;
        }
        return new EmployeeDto(entity.getId(), entity.getName(), entity.getSalary(), entity.getDepartment());
    }

    @Override
    public Employee dtoToEntity(EmployeeDto dto) {
        if (dto == null) {
            return null;
        }
        return new Employee(dto.getId(), dto.getName(), dto.getSalary(), dto.getDepartment());
    }
}