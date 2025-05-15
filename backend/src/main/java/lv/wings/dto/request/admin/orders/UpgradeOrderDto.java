package lv.wings.dto.request.admin.orders;

import lombok.Getter;
import lombok.Setter;
import lv.wings.enums.TranslationMethod;

@Getter
@Setter
public class UpgradeOrderDto {
    private String additionalComment;
    private TranslationMethod translateMethod;
}
