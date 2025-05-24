package lv.wings.dto.request.admin.products;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.interfaces.HasTranslationMethod;
import lv.wings.enums.TranslationMethod;

@Getter
@Setter
public class NewProductDto implements HasTranslationMethod {
    private TranslationMethod translationMethod; // not doing if present check, cause if is not will fallback to AUTO

    @NotNull(message = "{price.required}")
    @Positive(message = "{price.positive}")
    private BigDecimal price;

    @NotNull(message = "{amount.required}")
    @Positive(message = "{amount.min}")
    private Integer amount;

    @NotNull(message = "{categoryId.required}")
    private Integer categoryId;

    @NotEmpty(message = "{translations.empty}")
    @Valid
    private List<CreateProductTranslationDto> translations;

    @Valid
    private List<NewProductMaterialDto> materials;

    private List<MultipartFile> images;

    private List<Integer> colors;
}
