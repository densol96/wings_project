package lv.wings.DTO.object;

import lombok.Getter;
import lombok.Setter;
import lv.wings.DTO.DTOMeta;

@Getter
@Setter
public class ProductPictureDTO extends DTOObject {
	@DTOMeta(sourceField="productPictureId")
	public int id;
	
	public String picture;
	
	public String description;
}
