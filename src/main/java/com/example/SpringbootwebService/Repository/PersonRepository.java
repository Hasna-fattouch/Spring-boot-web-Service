package com.example.SpringbootwebService.Repository;

import com.example.SpringbootwebService.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE (:firstName IS NULL OR LOWER(p.firstName) LIKE LOWER(:firstName)||'%' OR LOWER(p.firstName) LIKE '%'|| LOWER(:firstName))" +
            "AND (:lastName IS NULL OR LOWER(p.lastName) LIKE LOWER(:lastName)||'%' OR LOWER(p.lastName) LIKE '%' || LOWER(:lastName))")
    List<Person> findAllWithFilters(String firstName, String lastName);

    Person findById(UUID id);

    void deleteById(UUID id);
}
