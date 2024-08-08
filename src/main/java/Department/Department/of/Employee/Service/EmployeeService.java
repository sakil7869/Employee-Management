package Department.Department.of.Employee.Service;

import Department.Department.of.Employee.DTO.EmployeeDTO;
import Department.Department.of.Employee.Entity.EmployeeEntity;
import Department.Department.of.Employee.Repository.EmployeeRepository;
import org.apache.el.util.ReflectionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id){
        return employeeRepository.findById(id)
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployee(){
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee){
        EmployeeEntity employeeEntity = modelMapper.map(inputEmployee,EmployeeEntity.class);
        EmployeeEntity employeeEntity1 = employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeEntity1,EmployeeDTO.class);
    }

    public boolean isPresentByEmployeeId(Long employeeId) throws ReflectiveOperationException {
        boolean isPresent = employeeRepository.existsById(employeeId);
        if (!isPresent)
            throw new ReflectiveOperationException("Element Not Found With id :"+ employeeId);
        return isPresent;
    }

    public EmployeeDTO updateNewEmployee(EmployeeDTO employeeDTO, Long employeeId) throws ReflectiveOperationException {
        isPresentByEmployeeId(employeeId);
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO , EmployeeEntity.class);
        EmployeeEntity employeeEntity1 = employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeEntity1 , EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long employeeId) throws ReflectiveOperationException {
        isPresentByEmployeeId(employeeId);
        employeeRepository.deleteById(employeeId);
        return true;
    }


    public EmployeeDTO updatePartiallyEmployeeId(Long employeeId, Map<String, Objects> updates) throws ReflectiveOperationException {
        isPresentByEmployeeId(employeeId);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }
}
