package lv.wings.dto.object;

import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.DTOMeta;


@Getter
@Setter
public class ProductPictureDTO extends DTOObject {
	@DTOMeta(sourceField="productPictureId")
	public int id;
	
	public String referenceToPicture;
	
	public String description;
}
