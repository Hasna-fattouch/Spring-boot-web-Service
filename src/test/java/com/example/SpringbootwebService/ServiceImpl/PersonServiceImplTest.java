package com.example.SpringbootwebService.ServiceImpl;

import com.example.SpringbootwebService.Model.Person;
import com.example.SpringbootwebService.Repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {
    @Mock
    private PersonRepository personRepository;

    private PersonServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new PersonServiceImpl(personRepository);
    }

    @Test
    void findAllWithFilters() {
        // Given
        String searchFirstName = "Rachel";
        String searchLastName = "Joe";
        // When
        List<Person> result = underTest.findAllWithFilters(searchFirstName, searchLastName);
        // then
        verify(personRepository).findAllWithFilters(searchFirstName, searchLastName);

    }

    @Test
    void canGetPersonById() {
        UUID id = UUID.randomUUID();
        Person person = new Person("John", "Doe");
        person.setId(id);
        given(personRepository.findById(id)).willReturn(person);
        // When
        Person expected = underTest.getPersonById(id);
        // Then
        assertThat(person.getId()).isEqualTo(expected.getId());
        assertThat(person.getFirstName()).isEqualTo(expected.getFirstName());
        assertThat(person.getLastName()).isEqualTo(expected.getLastName());

    }

    @Test
    void itShouldThrowNotFoundExceptionIfPersonNotExist() {
        // Given
        UUID id = UUID.randomUUID();
        //when
        given(personRepository.findById(id)).willReturn(null);
        // Then
        assertThatThrownBy(() -> underTest.getPersonById(id))
                .hasMessageContaining("Person does not exist");

    }

    @Test
    void itShouldThrowBadExceptionIfPersonNotExist() {
        // Given
        UUID id = null;
        // Then
        assertThatThrownBy(() -> underTest.getPersonById(id))
                .hasMessageContaining("Id must be not null");
    }

    @Test
    void canAddPerson() {
        // given
        UUID id = UUID.randomUUID();
        Person person = new Person("John", "Doe");
        //when
        underTest.addPerson(person);
        //then
        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personRepository).save(personArgumentCaptor.capture());
        Person capturedPerson = personArgumentCaptor.getValue();
        assertThat(capturedPerson).isEqualTo(person);
    }

    @Test
    void willThrowBadRequestExceptionIfIdNotNullAndExistPerson() {
        // given
        Person person = new Person(UUID.randomUUID(), "John", "Doe");
        //then
        assertThatThrownBy(() -> underTest.addPerson(person))
                .hasMessageContaining("Id person to add must be null");
    }

    @Test
    void canUpdatePerson() {
        // Given
        UUID id = UUID.randomUUID();
        Person person = new Person(id, "John", "Doe");
        given(personRepository.save(person)).willReturn(person);
        given(personRepository.findById(id)).willReturn(person);
        // When
        Person expected = underTest.updatePerson(id, person);
        // Then
        assertThat(person.getId()).isEqualTo(expected.getId());
        assertThat(person.getFirstName()).isEqualTo(expected.getFirstName());
        assertThat(person.getLastName()).isEqualTo(expected.getLastName());
    }

    @Test
    public void testUpdatePersonWithNonExistingId() {
        // Given
        UUID id = UUID.randomUUID();
        Person person = new Person("Jane", "Doe");
        given(personRepository.findById(id)).willReturn(null);
        //then
        assertThatThrownBy(() -> underTest.updatePerson(id, person))
                .hasMessageContaining("Person with id" + id + "does not exist");

    }

    @Test
    void canDeletePersonById() {
        // given
        UUID id = UUID.randomUUID();
        Person person = new Person(id, "John", "Doe");
        given(personRepository.findById(id)).willReturn(person);
        //when
        underTest.deletePersonById(id);
        //then
        verify(personRepository).deleteById(id);
    }

    @Test
    void willThrowPersonNotFoundExceptionIfDeleteNotExistPerson() {
        // given
        UUID id = UUID.randomUUID();
        given(personRepository.findById(id)).willReturn(null);
        //then
        assertThatThrownBy(() -> underTest.deletePersonById(id))
                .hasMessageContaining("Person with id" + id + "does not exist");
    }

}