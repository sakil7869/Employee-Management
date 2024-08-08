package Department.Department.of.Employee.DTO;

import Department.Department.of.Employee.Annotation.EmployeeRoleValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name Is Required")
    @Size(min = 4,max = 12,message = "Name Should be in the rage of [4-12]")
    private String name;

    @Email
    private String email;

    @Max(value = 80,message = "Age can't greater than 80")
    @Min(value = 18,message = "Age can't less than 18 ")
    private Integer age;

    @NotBlank
    @EmployeeRoleValidation
    private String role;

    @NotNull
    @PastOrPresent
    private LocalDateTime dateOfJoining;

    @JsonProperty("isActive")
    @AssertTrue(message = "Employee should be active")
    private Boolean isActive;

    @DecimalMin(value = "65000.50",message = "Salary Must be greater than 65000.50")
    @DecimalMax(value = "150000.50",message = "Salary must be less than 150000.50")
    private Double salary;

}
