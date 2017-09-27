package wenc.shspider.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity  
@Table(name="gd_user")  
public class UserEntity{
	@Id
    @GeneratedValue
    @Column(name="usr_id")
    private int id;
	
	@Column(name="usr_name",nullable=false)  
    private String userName;
	
	@Column(name="usr_pwd",nullable=false)  
    private String password;
	
	@Column(name="privilege",nullable=false)
	private String priv;
	
	@Column(name="phone",nullable=false)
	private String phone="00000000";
	
	@Column(name="sta_id",nullable=false)
	private int staId;

	@Column(name="createtime",nullable=false)  
    private Date createTime = new Date();
	
	@Column(name="modifytime",nullable=false)  
    private Date modifyTime = new Date();
	
	@Column(name="isvalid",nullable=false)  
    private int isValid = 1;

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPriv() {
		return priv;
	}

	public void setPriv(String priv) {
		this.priv = priv;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getStaId() {
		return staId;
	}

	public void setStaId(int staId) {
		this.staId = staId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}
