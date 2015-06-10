package application.domain;


import javax.persistence.*;
import java.io.Serializable;

/**
 * persistent class for the database table reservation
 */
@Entity
@NamedQuery(name = "TableStatus.findAll", query = "SELECT t FROM TableStatus t")
public class TableStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	//possible status of a table
	/**
	 * table is free
	 */
	public static final int TABLESTATUS_FREE = 1;
	/**
	 * table is reserved
	 */
	public static final int TABLESTATUS_RESERVED = 2;
	/**
	 * table is occupied
	 */
	public static final int TABLESTATUS_OCCUPIED = 3;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pk_id_table_status", unique = true, nullable = false)
	private int pkIdTablestatus;

	@Column(name = "status", nullable = true)
	private String status;
public TableStatus(){

}
	public TableStatus(int stat) {
		this.setPkIdTablestatus(stat);
	}

	public int getPkIdTablestatus() {
		return pkIdTablestatus;
	}

	public void setPkIdTablestatus(int pkIdTablestatus) {
		this.pkIdTablestatus = pkIdTablestatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Table{" +
				"pkIdTablestatus=" + pkIdTablestatus +
				"status=" + status +
				'}';
	}
}
