package by.trubetski.spring1.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fullname")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Author should be between 2 and 100 characters")
    private String fullName;
    @Column(name = "age")
    @Min(value = 1900, message = "Age should be greater then 0")
    @Max(value = 3000, message = "Age should be before 150")
    private int age;
    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person() {
    }

    public Person(String fullName, int age) {
        this.fullName = fullName;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
