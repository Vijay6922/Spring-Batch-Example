package Example.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import Example.entity.Department;

@Component
public class DepartmentProcessor implements ItemProcessor<Department, Department> {

    @Override
    public Department process(Department department) throws Exception {
        return department;
    }
}
