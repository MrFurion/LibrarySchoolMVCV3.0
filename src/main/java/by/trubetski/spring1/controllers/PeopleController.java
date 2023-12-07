package by.trubetski.spring1.controllers;

import by.trubetski.spring1.Services.PersonServices;
import by.trubetski.spring1.models.Person;
import by.trubetski.spring1.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/people")
public class PeopleController {
    private  final PersonServices personServices;
    private  final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonServices personServices, PersonValidator personValidator) {
        this.personServices = personServices;
        this.personValidator = personValidator;
    }
    @GetMapping
    public String index(Model model){
        model.addAttribute("people", personServices.findAll());

        return "people/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personServices.findOne(id));
        model.addAttribute("books", personServices.getBooksByPersonId(id));
        return "people/show";
    }
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        personServices.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personServices.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        personServices.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personServices.delete(id);
        return "redirect:/people";
    }
}
