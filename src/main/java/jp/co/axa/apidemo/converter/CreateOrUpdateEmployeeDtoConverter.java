package jp.co.axa.apidemo.converter;

import jp.co.axa.apidemo.dto.CreateOrUpdateEmployeeRequestDto;
import jp.co.axa.apidemo.entities.Employee;
import org.springframework.stereotype.Component;

/**
 * CreateOrUpdateEmployeeDtoConverter
 * Class that converts an
 * object of type CreateOrUpdateEmployeeRequestDto to an
 * object of type Employee (and vice versa).
 */
@Component
public class CreateOrUpdateEmployeeDtoConverter extends AbstractConverter<Employee, CreateOrUpdateEmployeeRequestDto>{

    @Override
    public CreateOrUpdateEmployeeRequestDto entityToDto(Employee entity) {
        if (entity == null) {
            return null;
        }
        return new CreateOrUpdateEmployeeRequestDto(entity.getName(), entity.getSalary(), entity.getDepartment());
    }

    @Override
    public Employee dtoToEntity(CreateOrUpdateEmployeeRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setSalary(dto.getSalary());
        employee.setDepartment(dto.getDepartment());
        return employee;
    }
}