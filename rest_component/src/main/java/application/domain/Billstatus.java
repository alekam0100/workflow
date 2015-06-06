package application.domain;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the billstatus database table.
 * 
 */
@Entity
@NamedQuery(name="Billstatus.findAll", query="SELECT b FROM Billstatus b")
public class Billstatus implements Serializable {
	private static final long serialVersionUID = 1L;
	
    // possible status of a bill (int-value referring to the database-id of the status):
    /**
     * bill is still open (not payed)
     */
    public static final int BILLSTATUS_OPEN = 1;
    /**
     * bill is closed (payed)
     */
    public static final int BILLSTATUS_CLOSED = 2;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pk_id_billstatus", unique=true, nullable=false)
	private int pkIdBillstatus;

	@Column(length=45)
	private String status;

	public Billstatus() {
	}

	public int getPkIdBillstatus() {
		return this.pkIdBillstatus;
	}

	public void setPkIdBillstatus(int pkIdBillstatus) {
		this.pkIdBillstatus = pkIdBillstatus;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}