package jp.co.axa.apidemo.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CreateOrUpdateEmployeeRequestDto
 * Data Transfer Object for create
 * and update employee method.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateEmployeeRequestDto implements Serializable {

    private String name;

    private Integer salary;

    private String department;

    @Override
    public String toString() {
        return "CreateOrUpdateEmployeeRequestDto{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
    }
}
