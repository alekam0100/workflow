package application.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import application.domain.Test;

public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findByText(String text);
    
    
}
