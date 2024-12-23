package lv.wings.DTO.object;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryDTO extends DTOObject {
	public int productCategoryId;
	
	public String title;

	public String description;
}
