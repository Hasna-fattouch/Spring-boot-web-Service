package com.example.SpringbootwebService.Controller;

import com.example.SpringbootwebService.Exception.BadRequestException;
import com.example.SpringbootwebService.Exception.PersonNotFoundException;
import com.example.SpringbootwebService.Model.Person;
import com.example.SpringbootwebService.Model.ResponseObject;
import com.example.SpringbootwebService.Service.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/person")
@Transactional
@Slf4j
public class PersonController {
    private final PersonService personService;

    /**
     * Find list of person filtered by searchFirstName and searchLastName
     *
     * @param firstName
     * @param lastName
     * @return list of person only matched the beginning and end of the first name and last name
     */
    @GetMapping("/WithFilters")
    public ResponseEntity<List<Person>> getAllWithFilters(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        log.debug("REST request to get matched list of person : ");
        return ResponseEntity.status(HttpStatus.OK).body(personService.findAllWithFilters(firstName, lastName));
    }

    /**
     * Find person by id
     *
     * @param id : id person
     * @return Object Response
     */
    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<ResponseObject> getPersonById(@PathVariable("id") UUID id) {
        log.debug("REST request to get a person : {}", id);
        ResponseObject response = new ResponseObject();
        try {
            Person person = personService.getPersonById(id);
            response.setMessage("Person with id" + id + " does exist");
            response.setPerson(person);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (BadRequestException e) {
            response.setMessage("Person id must be not null or empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (PersonNotFoundException e) {
            response.setMessage("Person with id" + id + " does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    /**
     * Create a new person
     *
     * @param person to create
     * @return object response
     */
    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addPerson(@RequestBody @Valid Person person) {
        log.debug("REST request to save a new person : {}", person);
        ResponseObject response = new ResponseObject();
        try {
            Person savedPerson = personService.addPerson(person);
            response.setMessage("Person successfully added");
            response.setPerson(savedPerson);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException e) {
            response.setMessage("Person already exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Find and edit person by id
     *
     * @param id     person id
     * @param person
     * @return object response have updated person and message
     */
    @PutMapping(value = "/update/{id}", produces = "application/json")
    public ResponseEntity<ResponseObject> updatePerson(@PathVariable("id") UUID id, @RequestBody @Valid Person person) {
        log.debug("REST request to update person : {}", person);
        ResponseObject response = new ResponseObject();
        try {
            Person updatedPerson = personService.updatePerson(id, person);
            response.setMessage("Person successfully updated");
            response.setPerson(updatedPerson);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (PersonNotFoundException e) {
            response.setMessage("Person with id" + id + " does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Delete person by id
     *
     * @param id person id
     * @return object response and message
     */
    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<ResponseObject> deletePersonById(@PathVariable("id") UUID id) {
        log.debug("REST request to delete a person : {}", id);
        ResponseObject response = new ResponseObject();
        try {
            personService.deletePersonById(id);
            response.setMessage("Person successfully deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (PersonNotFoundException e) {
            response.setMessage("Person with id" + id + " does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
