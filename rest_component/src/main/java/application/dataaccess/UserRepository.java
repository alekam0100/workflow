package application.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import application.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByUsername(@Param("username") String username);
	
	User findByToken(String token);
	
	@Modifying
	@Query("UPDATE User u SET u.token=NULL WHERE u.pkIdUser= :id")
	void deleteToken(@Param("id") int id);
	
	@Modifying
	@Query("UPDATE User u SET u.token=NULL WHERE u.username= :username")
	void deleteTokenByUser(@Param("username") String username);
	
	@Modifying
	@Query("UPDATE User u SET u.token=NULL WHERE u.token= :token")
	void deleteToken(@Param("token") String token);
	
	@Modifying
	@Query("UPDATE User u SET u.token= :token WHERE u.username= :username")
	void addToken(@Param("username") String username, @Param("token") String token);
}
