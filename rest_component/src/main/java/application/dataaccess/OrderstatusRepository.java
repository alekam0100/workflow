package application.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import application.domain.Orderstatus;

public interface OrderstatusRepository extends JpaRepository<Orderstatus, Integer> {

}
