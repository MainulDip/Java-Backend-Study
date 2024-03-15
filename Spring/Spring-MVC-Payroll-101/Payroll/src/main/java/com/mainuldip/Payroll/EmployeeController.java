package com.mainuldip.Payroll;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> all() {
        List<EntityModel<Employee>> employees = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of (
                employees, linkTo( methodOn(EmployeeController.class).all() ).withSelfRel()
        );
    }

    // end::get-aggregate-root[]
    // curl -v localhost:8080/employees | json_pp

    

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
    EntityModel<Employee> one(@PathVariable @NonNull Long id) {
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
//        return EntityModel.of(employee,
//                linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
//                linkTo(methodOn(EmployeeController.class).all()).withRel("employees")
//        );


        return assembler.toModel(employee);
    }
    // curl -v localhost:8080/employees/99

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable @NonNull Long id) throws Exception {
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

// adding spring's @Component annotation, so-that the assembler will be automatically created when the app starts.
@Component
class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

    // toModel is the method of RepresentationalModelAssembler that is marked `abstract`, which must be implemented
    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees")
                );
    }
}