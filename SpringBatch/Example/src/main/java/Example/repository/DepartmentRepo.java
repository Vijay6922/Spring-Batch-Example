package Example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Example.entity.Department;

public interface DepartmentRepo extends JpaRepository <Department,String>{

}
