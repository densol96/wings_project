package lv.wings.DTO.object;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lv.wings.model.Product;

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

	public int imageCount;

	@Override
	public void onFinish(Object source) {
		this.imageCount = ((Product) source).getProductPicture().size();
	}
}
