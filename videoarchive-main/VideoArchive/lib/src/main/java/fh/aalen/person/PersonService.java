package fh.aalen.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    public Iterable<Person> getAllPersons() {
        return repository.findAll();
    }

    public Optional<Person> getPersonById(Long id) {
        return repository.findById(id);
    }

    public Person addPerson(Person person) {
        return repository.save(person);
    }

    public Person updatePerson(Long id, Person personDetails) {
        return repository.findById(id).map(p -> {
            p.setSurename(personDetails.getSurename());
            p.setBirthdate(personDetails.getBirthdate());
            return repository.save(p);
        }).orElseThrow(() -> new RuntimeException("Person nicht gefunden"));
    }

    public void deletePerson(Long id) {
        repository.deleteById(id);
    }
}
