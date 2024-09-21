package project.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    @NotBlank(message = "Адрес не может быть пустым")
    @Size(max = 255, message = "Адрес не должен превышать 255 символов")
    private String address;
}
