package com.example.SpringbootwebService.Service;

import com.example.SpringbootwebService.Model.Person;

import java.util.List;
import java.util.UUID;

public interface PersonService {

    List<Person> findAllWithFilters(String firstName, String lastName);

    Person getPersonById(UUID id);

    Person addPerson(Person person);

    Person updatePerson(UUID id, Person person);

    void deletePersonById(UUID id);

}
