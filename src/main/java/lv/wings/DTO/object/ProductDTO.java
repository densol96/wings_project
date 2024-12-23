package lv.wings.DTO.object;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO extends DTOObject {
	public int productId;

	public ProductCategoryDTO productCategory;
	
	public ArrayList<ProductPictureDTO> productPicture;

	public String title;

	public String description;

	public float price;

	public int amount;
}
