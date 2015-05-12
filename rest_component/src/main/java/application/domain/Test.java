package application.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the test database table.
 * 
 */
@Entity
@NamedQuery(name="Test.findAll", query="SELECT t FROM Test t")
public class Test implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pk_idTest", unique=true, nullable=false)
	private int pkIdTest;

	@Column(length=45)
	private String text;

	public Test() {
	}

	public int getPkIdTest() {
		return this.pkIdTest;
	}

	public void setPkIdTest(int pkIdTest) {
		this.pkIdTest = pkIdTest;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}