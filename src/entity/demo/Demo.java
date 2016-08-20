package entity.demo;

import javax.persistence.*;
import entity.AbstractEntity;


@Entity
@Table(name="demo")
public class Demo extends AbstractEntity{
	private String demoID;
	private String name;
	private int age;

	@Id
	public String getDemoID() {
		return demoID;
	}

	public void setDemoID(String demoID) {
		this.demoID = demoID;
	}
	
	@Column
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
