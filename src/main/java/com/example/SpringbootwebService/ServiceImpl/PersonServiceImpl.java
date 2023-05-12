package com.example.SpringbootwebService.ServiceImpl;

import com.example.SpringbootwebService.Exception.BadRequestException;
import com.example.SpringbootwebService.Exception.PersonNotFoundException;
import com.example.SpringbootwebService.Model.Person;
import com.example.SpringbootwebService.Repository.PersonRepository;
import com.example.SpringbootwebService.Service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public List<Person> findAllWithFilters(String searchFirstName, String searchLastName) {
        return personRepository.findAllWithFilters(searchFirstName, searchLastName);

    }

    @Override
    public Person getPersonById(UUID id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("Id must be not null");
        }
        Person person = personRepository.findById(id);
        if (Objects.isNull(person)) {
            throw new PersonNotFoundException("Person does not exist");
        }
        return person;
    }


    @Override
    public Person addPerson(Person person) {
        if (Objects.nonNull(person.getId())) {
            throw new BadRequestException("Id person to add must be null");
        }
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(UUID id, Person person) {
        Person personToUpdated = personRepository.findById(id);
        if (Objects.isNull(personToUpdated)) {
            throw new PersonNotFoundException("Person with id" + id + "does not exist");
        }
        BeanUtils.copyProperties(person, personToUpdated, "id");
        return personRepository.save(personToUpdated);
    }

    @Override
    public void deletePersonById(UUID id) {
        if (Objects.isNull(personRepository.findById(id))) {
            throw new PersonNotFoundException("Person with id" + id + "does not exist");
        }
        personRepository.deleteById(id);
    }
}
