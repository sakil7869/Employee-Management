package Department.Department.of.Employee.Controller;

import Department.Department.of.Employee.DTO.EmployeeDTO;
import Department.Department.of.Employee.Exception.ResourceNotFoundException;
import Department.Department.of.Employee.Service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO>getEmployeeById(@PathVariable(name = "employeeId") Long id){
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);
        return employeeDTO
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElseThrow(()->new ResourceNotFoundException("Resource Not Found With Id: "+id ));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee(@RequestParam(required = false) Integer age,
                                                            @RequestParam(required = false) String sortBy){
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee){
        EmployeeDTO employeeDTO = employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(employeeDTO , HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateNewEmployee(@RequestBody @Valid EmployeeDTO employeeDTO , @PathVariable Long employeeId) throws ReflectiveOperationException {
        return ResponseEntity.ok(employeeService.updateNewEmployee(employeeDTO,employeeId));
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean>deleteEmployeeById(@PathVariable @Valid Long employeeId) throws ReflectiveOperationException {
        boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);
        if (gotDeleted)
            return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartiallyEmployeeId(@RequestBody @Valid Map<String , Objects> updates,
                                                                 @PathVariable Long employeeId) throws ReflectiveOperationException {
        EmployeeDTO employeeDTO = employeeService.updatePartiallyEmployeeId(employeeId , updates);
        if (employeeDTO == null)
            return ResponseEntity.notFound().build();
            return ResponseEntity.ok(employeeDTO);
    }
}
