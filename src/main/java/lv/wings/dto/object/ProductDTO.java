package lv.wings.dto.object;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.DTOMeta;
import lv.wings.model.Product;


@Getter
@Setter
public class ProductDTO extends DTOObject {
	@DTOMeta(sourceField="productId")
	public int id;


	@NotNull
	@Size(min = 4, max = 50)
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ ]+", message = "Only letters and space are allowed")
	public String title;


	@Column(name = "description")
	@NotNull
	@Size(min = 4, max = 1000)
	public String description;

	@NotNull
	@Min(0)
	public float price;

	@NotNull
	@Min(0)
	public int amount;

	public ProductCategoryDTO productCategory;
	
	public ArrayList<ProductPictureDTO> productPictures;

	@DTOMeta(ignore=true)
	public int imageCount;

	@Override
	public void onFinish(Object source) {
		this.imageCount = ((Product) source).getProductPictures().size();
	}
}
