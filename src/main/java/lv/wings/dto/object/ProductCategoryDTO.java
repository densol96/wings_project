package lv.wings.dto.object;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.DTOMeta;


@Getter
@Setter
public class ProductCategoryDTO extends DTOObject {
	@DTOMeta(sourceField="productCategoryId")
	public int id;
	

	@Size(min = 3, max = 200, message = "Kategorijas nosaukums nedrīkst saturēt mazāk par 3 vai vairāk par 200 rakstzīmēm!")
	@Pattern(regexp = "^[a-zA-ZāčēģīķļņōŗšūžĀČĒĢĪĶĻŅŌŖŠŪŽ\\s]+$", message = "Kategorijas nosaukums drīkst saturēt tikai burtus un atstarpes!")
	public String title;

	@Size(min = 4, max = 150)
	public String description;
}
