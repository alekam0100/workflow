package application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="fk_id_user", unique=true, nullable=false)
	private Integer fkIdUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="birth_date")
	private Date birthDate;

	@Column(length=45)
	private String city;

	@Column(length=45)
	private String country;

	@Column(length=45)
	private String email;

	@Column(name="first_name", length=45)
	private String firstName;

	@Column(name="house_no", length=45)
	private String houseNo;

	@Column(name="last_name", length=45)
	private String lastName;

	@Column(name="post_code", length=45)
	private String postCode;

	@Column(length=45)
	private String street;

	//bi-directional one-to-one association to User
	@OneToOne (cascade = {CascadeType.ALL})
	@JoinColumn(name="fk_id_user", nullable=false, insertable=false, updatable=false)
	//@JsonIgnore
	private User user;

	public Customer() {
	}

	public int getFkIdUser() {
		return this.fkIdUser;
	}

	public void setFkIdUser(int fkIdUser) {
		this.fkIdUser = fkIdUser;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getHouseNo() {
		return this.houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}