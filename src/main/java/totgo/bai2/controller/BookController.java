package totgo.bai2.controller;

import org.springframework.util.StringUtils;
import totgo.bai2.model.AppConfig;
import totgo.bai2.model.Book;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private List<Book> listBook;

    @Autowired
    private AppConfig appConfig;

//    @GetMapping("/")
//    public String showAllBooks(Model model) {
//        model.addAttribute("listBook", listBook);
//        model.addAttribute("title", "Danh sách cuốn sách");
//        return "book/list";
//    }

    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book/add";
    }



    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult result, @RequestParam("imageFile") MultipartFile imageFile) {
        if (result.hasErrors()) {
            return "book/add";
        }


        if (!imageFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
            try {
                Path uploadPath = Paths.get("src/main/resources/static/images/");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                book.setImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return "book/add";
            }
        }

        int newId = listBook.stream().mapToInt(Book::getId).max().orElse(0) + 1;
        book.setId(newId);
        listBook.add(book);
        return "redirect:/";
    }

    @GetMapping("/books/edit/{id}")
    public String editBookForm(@PathVariable("id") int id, Model model) {
        Optional<Book> book = listBook.stream().filter(b -> b.getId() == id).findFirst();
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "book/edit";
        } else {
            return "redirect:/";
        }
    }
    @PostMapping("/books/edit/{id}")
    public String updateBook(@PathVariable("id") int id, @Valid @ModelAttribute("book") Book book, BindingResult result, @RequestParam("imageFile") MultipartFile imageFile) {
        if (result.hasErrors()) {
            return "book/edit";
        }

        Book existingBook = listBook.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);

        if (!imageFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
            try {
                Path uploadPath = Paths.get("src/main/resources/static/images/");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                book.setImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return "book/edit";
            }
        } else {
            if (existingBook != null) {
                book.setImage(existingBook.getImage());
            }
        }

        if (existingBook != null) {
            int index = listBook.indexOf(existingBook);
            if (index >= 0) {
                listBook.set(index, book);
            }
        }

        return "redirect:/";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        listBook.removeIf(book -> book.getId() == id);
        return "redirect:/";
    }
    @RequestMapping("/")
    public String loginSubmit(Model model){
        model.addAttribute("listBook", listBook);
        return "/pages/landing_page";
    }
}