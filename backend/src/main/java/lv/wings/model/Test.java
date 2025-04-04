package lv.wings.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Test extends TestParent {
    @Email(message = "Should be an email!")
    private String name;
}
