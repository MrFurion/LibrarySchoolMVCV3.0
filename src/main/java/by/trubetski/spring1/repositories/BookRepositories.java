package by.trubetski.spring1.repositories;

import by.trubetski.spring1.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepositories extends JpaRepository<Book, Integer> {
    List<Book> findByTitleStartingWith(String title);
}
