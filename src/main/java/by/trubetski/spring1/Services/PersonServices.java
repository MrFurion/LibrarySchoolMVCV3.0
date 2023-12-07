package by.trubetski.spring1.Services;

import by.trubetski.spring1.models.Book;
import by.trubetski.spring1.models.Person;
import by.trubetski.spring1.repositories.PersonRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.hibernate.Hibernate;



import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonServices {
    private final PersonRepositories personRepositories;

    @Autowired
    public PersonServices(PersonRepositories personRepositories) {
        this.personRepositories = personRepositories;
    }
    public List<Person> findAll(){
        return personRepositories.findAll();
    }
    public Person findOne(int id){
        Optional<Person>personId = personRepositories.findById(id);
        return personId.orElse(null);
    }
    @Transactional
    public void save(Person person){
        personRepositories.save(person);
    }
    @Transactional
    public void update(int id, Person person){
        person.setId(id);
        personRepositories.save(person);
    }
    @Transactional
    public void delete(int id){
        personRepositories.deleteById(id);
    }
    public Optional<Person> getPersonByFullName(String fullName) {
        return personRepositories.findByFullName(fullName);
    }
    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = personRepositories.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());

            person.get().getBooks().forEach(book -> {
                long diffInMillies = Math.abs(book.getTakenAt().getTime() - new Date().getTime());

                if (diffInMillies > 864000000)
                    book.setExpired(true);
            });

            return person.get().getBooks();
        }
        else {
            return Collections.emptyList();
        }
    }

}
