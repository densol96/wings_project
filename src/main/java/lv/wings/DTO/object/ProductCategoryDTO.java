package lv.wings.DTO.object;

import lombok.Getter;
import lombok.Setter;
import lv.wings.DTO.DTOMeta;

@Getter
@Setter
public class ProductCategoryDTO extends DTOObject {
	@DTOMeta(sourceField="productCategoryId")
	public int id;
	
	public String title;

	public String description;
}
