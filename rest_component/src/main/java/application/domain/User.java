package application.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

    public User(String username, String password) {
            this.username = username;
            this.password = password;
    }
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pk_id_user", unique=true, nullable=false)
	private int pkIdUser;

	@Column(length=45)
	//@JsonIgnore
	private String password;

	@Column(length=64)
	private String token;

	@Column(nullable=false, length=45)
	private String username;

	//bi-directional one-to-one association to Customer
	@OneToOne(mappedBy="user", optional = true)
	@JsonIgnore
	private Customer customer;

	public User() {
	}

	public int getPkIdUser() {
		return this.pkIdUser;
	}

	public void setPkIdUser(int pkIdUser) {
		this.pkIdUser = pkIdUser;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return String.format("User: [pk_id_user=%d]", pkIdUser);
	}
}