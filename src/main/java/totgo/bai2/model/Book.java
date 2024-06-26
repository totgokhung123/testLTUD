package totgo.bai2.model;

import jakarta.validation.constraints .*;
import lombok .*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    private int id;


    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 100, message = "Tiêu đề không được vượt quá 100 ký tự")
    private String title;

    @NotBlank(message = "Tác giả không được để trống")
    @Size(max = 100, message = "Tên tác giả không được vượt quá 100 ký tự")
    private String author;

    @NotNull(message = "Giá không được để trống")
    @Min(value = 0, message = "Gia phải là một số không âm")
    private Double price;

    @NotBlank(message = "Thể loại không được để trống")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Thể loại chi chấp nhận chữ cái và khoảng trắng")
    private String category;
    private String image; // Thêm trường lưu trữ tên ảnh
    //alo test
    //test
}
