package com.example.SpringbootwebService.Repository;

import com.example.SpringbootwebService.Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    PersonRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    public void itShouldReturnedMachedListPerson() {
        // Given
        List<Person> expected = Arrays.asList(
                new Person("Rachel", "Joe"),
                new Person("Rami", "Joe")
        );
        // When
        underTest.saveAll(expected);
        // Then
        List<Person> actual = underTest.findAllWithFilters("Ra", "Jo");
        assertEquals(expected, actual);
    }

}