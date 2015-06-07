package application.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;


/**
 * The persistent class for the waiterstatus database table.
 * 
 */
@Entity
@NamedQuery(name="Waiterstatus.findAll", query="SELECT w FROM Waiterstatus w")
public class Waiterstatus implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final int WAITERSTATUS_NONE = 1;
	
	public static final int WAITERSTATUS_WAITER = 2;
	
	public static final int WAITERSTATUS_BILL = 3;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pk_id_waiter_status", unique=true, nullable=false)
	private int pkIdWaiterstatus;

	@Column(length=45)
	private String status;

	//bi-directional many-to-one association to Table
	@OneToMany(mappedBy="waiterstatus")
	@JsonBackReference(value="table-waiterstatus")
	private List<RestaurantTable> tables;

	public Waiterstatus() {
	}

	public int getPkIdWaiterstatus() {
		return this.pkIdWaiterstatus;
	}

	public void setPkIdWaiterstatus(int pkIdWaiterstatus) {
		this.pkIdWaiterstatus = pkIdWaiterstatus;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<RestaurantTable> getTables() {
		return this.tables;
	}

	public void setTables(List<RestaurantTable> tables) {
		this.tables = tables;
	}

	public RestaurantTable addTable(RestaurantTable table) {
		getTables().add(table);
		table.setWaiterstatus(this);

		return table;
	}

	public RestaurantTable removeTable(RestaurantTable table) {
		getTables().remove(table);
		table.setWaiterstatus(null);

		return table;
	}

}