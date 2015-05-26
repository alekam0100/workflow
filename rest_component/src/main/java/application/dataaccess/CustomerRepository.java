package application.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import application.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByLastName(String lastName);
    Customer findByfkIdUser(int fkIdUser);
}