package lv.wings.dto.object;

import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.DTOMeta;

@Getter
@Setter
public class ProductCategoryDTO extends DTOObject {
	@DTOMeta(sourceField="productCategoryId")
	public int id;
	
	public String title;

	public String description;
}
