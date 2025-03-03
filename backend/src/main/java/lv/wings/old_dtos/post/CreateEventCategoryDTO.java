package lv.wings.old_dtos.post;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CreateEventCategoryDTO {

    @NotNull
	@Size(min = 1, max = 200, message = "Kategorijas nosaukums nedrīkst saturēt mazāk par 1 vai vairāk par 200 rakstzīmēm!")
    private String title;
}
