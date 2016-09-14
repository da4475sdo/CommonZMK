package entity.projectDetail;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import entity.AbstractEntity;

@Entity(name="projectDetail")
@Table(name="projectDetail")
public class ProjectDetail extends AbstractEntity{
	private String projectDetailID;
	private String projectID;
	private String projectPic;
	private String projectDescription;
	
	@Id
	public String getProjectDetailID() {
		return projectDetailID;
	}
	public void setProjectDetailID(String projectDetailID) {
		this.projectDetailID = projectDetailID;
	}
	
	@Column
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	
	@Column
	public String getProjectPic() {
		return projectPic;
	}
	public void setProjectPic(String projectPic) {
		this.projectPic = projectPic;
	}
	
	@Column
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
}
