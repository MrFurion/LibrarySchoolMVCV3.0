package by.trubetski.spring1.repositories;

import by.trubetski.spring1.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepositories extends JpaRepository<Person, Integer> {
    Optional<Person> findByFullName(String fullName);
}
