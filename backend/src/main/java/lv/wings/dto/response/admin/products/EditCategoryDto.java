package lv.wings.dto.response.admin.products;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditCategoryDto {
    private Integer id;
    private List<ExistingCategoryTranslationDto> translations;
}
