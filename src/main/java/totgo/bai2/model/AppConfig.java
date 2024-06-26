package totgo.bai2.model;

import  totgo.bai2.model.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class AppConfig {
   // private final AtomicInteger counter = new AtomicInteger(3); // Khởi tạo với giá trị hiện tại

    @Bean
    public List<Book> getBooks(){
        List<Book> listBooks = new ArrayList<>();

        // Tạo sách bằng cách sử dụng Setter - NoArgsConstructor Lombok
        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("kenh seg");
        book1.setAuthor("Le Dao Tuan Hung");
        book1.setPrice(47.0);
        book1.setCategory("Seg");
        book1.setImage("book2.jpg");
        listBooks.add(book1);

        // Tạo sách bằng cách sử dụng AllArgsConstructor Lombok
        Book book2 = new Book(2,"Design Patterns: Elements of Reusable Object-Oriented Software",
                "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides",55.0,"Software Design","book3.jpg");
        listBooks.add(book2);
        Book book3 = Book.builder()
                .id(3)
                .title("Effective Java")
                .author("Joshua Bloch")
                .price(45.0)
                .category("Programming")
                .image("book1.jpg")
                .build();
        listBooks.add(book3);
        return listBooks;
    }

//    public int getNextId() {
//        return counter.incrementAndGet();
//    }
}