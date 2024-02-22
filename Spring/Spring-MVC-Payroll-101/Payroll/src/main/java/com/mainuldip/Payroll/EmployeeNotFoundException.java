package com.mainuldip.Payroll;

public class EmployeeNotFoundException extends  RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}
