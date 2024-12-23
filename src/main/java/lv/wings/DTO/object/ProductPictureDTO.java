package lv.wings.DTO.object;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPictureDTO extends DTOObject {
	public int productPictureId;
	
	public String picture;
	
	public String description;
}
