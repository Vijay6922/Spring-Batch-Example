package Example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Department_Info")
public class Department {

	@Id
	@Column(name = "EmployeeId")
	private String id;

	@Column(name = "DepartmentName")
	private String departmentName;

	@Column(name = "ManagerId")
	private int managerId;

	@Column(name = "ManagerName")
	private String managerName;

	public Department(String id, String departmentName, int managerId, String managerName) {
		super();
		this.id = id;
		this.departmentName = departmentName;
		this.managerId = managerId;
		this.managerName = managerName;
	}

	public Department() {
		super();
	}

	public String getName() {
		return managerName;
	}

	public void setName(String name) {
		this.managerName = name;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setContact(int managerId) {
		this.managerId = managerId;
	}

}
