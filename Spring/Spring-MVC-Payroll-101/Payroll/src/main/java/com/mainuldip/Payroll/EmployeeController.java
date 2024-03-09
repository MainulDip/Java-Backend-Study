package com.mainuldip.Payroll;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/employees")
    List<Employee> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]
    // curl -v localhost:8080/employees

    // add a new employee
    @PostMapping("/employees")
    boolean newEmployee(@RequestBody Employee newEmployee) {
        if (newEmployee != null) {
            repository.save(newEmployee);
            return true;
        }
        return false;
    }
    // curl -X POST localhost:8080/employees -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}'

    @GetMapping("/employees/{id}")
    Employee one(@PathVariable @NonNull Long id) {
        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }
    // curl -v localhost:8080/employees/99

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody  Employee newEmployee, @PathVariable @NonNull Long id) throws Exception {
        return repository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            return repository.save(employee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return repository.save(newEmployee);
        });
    }
    // curl -X PUT localhost:8080/employees/3 -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}'

    // Delete Employee
    @DeleteMapping("/employees/{id}")
    void deleteEmploy(@PathVariable Long id) {
        if (id != null) repository.deleteById(id);
    }
    // curl -X DELETE localhost:8080/employees/4
}


class EmployeeNotFoundException extends RuntimeException {
    EmployeeNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}

// this will actually connect the custom exception handler (EmployeeNotFoundException) with the Spring REST Controller Application
@ControllerAdvice
class EmployeeNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(EmployeeNotFoundException ex) {
        return ex.getMessage() + " Done!! \n\n\n";
    }
}