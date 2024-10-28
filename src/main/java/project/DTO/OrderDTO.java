package project.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for order details.
 * This class is used to transfer order-related data, particularly the delivery address.
 * It includes validation constraints to ensure valid data.
 */
@Getter
@Setter
public class OrderDTO {
    /**
     * The delivery address for the order.
     * This field must not be blank and must not exceed 255 characters.
     */
    @NotBlank(message = "Адрес не может быть пустым")
    @Size(max = 255, message = "Адрес не должен превышать 255 символов")
    private String address;
}
