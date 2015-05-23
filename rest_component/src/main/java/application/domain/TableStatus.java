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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pk_id_table_status", unique = true, nullable = false)
	private int pkIdTablestatus;

	@Column(name = "status", nullable = true)
	private String status;

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
