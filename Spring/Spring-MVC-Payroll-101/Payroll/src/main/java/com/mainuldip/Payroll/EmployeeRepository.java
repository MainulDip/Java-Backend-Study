package com.mainuldip.Payroll;

import org.springframework.data.jpa.repository.JpaRepository;


// It is indeed not necessary to put the @Repository annotation on interfaces that extend JpaRepository
interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
