package by.trubetski.spring1.controllers;

import by.trubetski.spring1.Services.BookServices;
import by.trubetski.spring1.Services.PersonServices;
import by.trubetski.spring1.models.Book;
import by.trubetski.spring1.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookServices bookServices;
    private final PersonServices personServices;

    public BooksController(BookServices bookServices, PersonServices personServices) {
        this.bookServices = bookServices;
        this.personServices = personServices;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {

        if (page == null || booksPerPage == null)
            model.addAttribute("books", bookServices.findAll(sortByYear));
        else
            model.addAttribute("books", bookServices.findWithPagination(page, booksPerPage, sortByYear));

        return "books/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookServices.findOne(id));

        Person bookOwner = bookServices.getBookOwner(id);

        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("people", personServices.findAll());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book Book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book Book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookServices.save(Book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookServices.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookServices.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookServices.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookServices.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookServices.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", bookServices.searchByTitle(query));
        return "books/search";
    }
}
