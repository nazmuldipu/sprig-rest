package com.unololtd.nazmul.springrest.repository;

import com.unololtd.nazmul.springrest.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public Employee findByName(String name);
}
