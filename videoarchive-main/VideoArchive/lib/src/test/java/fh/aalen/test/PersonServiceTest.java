package fh.aalen.test;

import fh.aalen.person.Person;
import fh.aalen.person.PersonRepository;
import fh.aalen.person.PersonService;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Example tests for PersonService demonstrating DataProvider, groups and
 * expected exception handling.
 */
public class PersonServiceTest {

    /**
     * Helper subclass to allow injecting a mock repository via constructor.
     */
    static class TestablePersonService extends PersonService {
        TestablePersonService(PersonRepository repo) {
            ReflectionTestUtils.setField(this, "repository", repo);
        }
    }

    /** Simple in-memory repository used instead of Mockito. */
    static class InMemoryPersonRepository implements PersonRepository {
        private final Map<Long, Person> store = new HashMap<>();
        private long nextId = 1L;

        @Override
        public <S extends Person> S save(S entity) {
            if (entity.getId() == null) {
                ReflectionTestUtils.setField(entity, "id", nextId++);
            }
            store.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public <S extends Person> Iterable<S> saveAll(Iterable<S> entities) {
            for (S e : entities) {
                save(e);
            }
            return entities;
        }

        @Override
        public Optional<Person> findById(Long id) {
            return Optional.ofNullable(store.get(id));
        }

        @Override
        public boolean existsById(Long id) {
            return store.containsKey(id);
        }

        @Override
        public Iterable<Person> findAll() {
            return store.values();
        }

        @Override
        public Iterable<Person> findAllById(Iterable<Long> ids) {
            List<Person> list = new ArrayList<>();
            for (Long id : ids) {
                if (store.containsKey(id)) {
                    list.add(store.get(id));
                }
            }
            return list;
        }

        @Override
        public long count() {
            return store.size();
        }

        @Override
        public void deleteById(Long id) {
            store.remove(id);
        }

        @Override
        public void delete(Person entity) {
            store.remove(entity.getId());
        }

        @Override
        public void deleteAllById(Iterable<? extends Long> ids) {
            for (Long id : ids) {
                store.remove(id);
            }
        }

        @Override
        public void deleteAll(Iterable<? extends Person> entities) {
            for (Person p : entities) {
                store.remove(p.getId());
            }
        }

        @Override
        public void deleteAll() {
            store.clear();
        }
    }

    private PersonRepository repository;
    private PersonService service;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        System.out.println("PersonServiceTest.setup");
        repository = new InMemoryPersonRepository();
        service = new TestablePersonService(repository);
    }

    @DataProvider(name = "personData")
    public Object[][] personData() {
        return new Object[][]{
                {"Mueller", LocalDate.of(1990,1,1)},
                {"Schmidt", LocalDate.of(1980,12,31)}
        };
    }

    @Test(dataProvider = "personData", groups = "crud", description = "Verify addPerson saves entity")
    public void testAddPerson(String name, LocalDate date) {
        Person p = new Person(name, date);
        Person saved = service.addPerson(p);
        assertEquals(saved.getSurename(), name);
        assertTrue(repository.findById(saved.getId()).isPresent());
    }

    @Test(groups = "crud", description = "Expect exception when updating unknown person", expectedExceptions = RuntimeException.class)
    public void testUpdatePersonNotFound() {
        Long id = 42L;
        service.updatePerson(id, new Person("X", LocalDate.now()));
    }
}