package application.domain;


import javax.persistence.*;
import java.io.Serializable;

/**
 * persistent class for the database table reservation
 */
@Entity
@NamedQuery(name = "RestaurantTable.findAll", query = "SELECT t FROM RestaurantTable t")
public class RestaurantTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pk_id_restaurant_table", unique = true, nullable = false)
	private int pkIdRestaurantTable;

	@Column(name = "max_person", nullable = false)
	private int maxPerson;

	@Column(name="fk_id_waiter_status", nullable=false)
	private int fkIdWaiterStatus;

	@Column(name="fk_id_table_status", nullable=false)
	private int fkIdTableStatus;

	@OneToOne
	@JoinColumn(name="fk_id_table_status", nullable=false, insertable=false, updatable=false)
	private TableStatus tableStatus;

	public int getPkIdRestaurantTable() {
		return pkIdRestaurantTable;
	}

	public void setPkIdRestaurantTable(int pkIdRestaurantTable) {
		this.pkIdRestaurantTable = pkIdRestaurantTable;
	}

	public int getMaxPerson() {
		return maxPerson;
	}

	public void setMaxPerson(int maxPerson) {
		this.maxPerson = maxPerson;
	}

	public int getFkIdWaiterStatus() {
		return fkIdWaiterStatus;
	}

	public void setFkIdWaiterStatus(int fkIdWaiterStatus) {
		this.fkIdWaiterStatus = fkIdWaiterStatus;
	}

	public TableStatus getTableStatus() {
		return tableStatus;
	}

	public void setTableStatus(TableStatus tableStatus) {
		this.tableStatus = tableStatus;
	}

	public int getFkIdTableStatus() {
		return fkIdTableStatus;
	}

	public void setFkIdTableStatus(int fkIdTableStatus) {
		this.fkIdTableStatus = fkIdTableStatus;
	}

	@Override
	public String toString() {
		return "Table{" +
				"pkIdRestaurantTable=" + pkIdRestaurantTable +
				"maxPerson=" + maxPerson +
				"tableStatus=" + tableStatus+
				"fkIdWaiterStatus=" + fkIdWaiterStatus +
				'}';
	}
}
