package lv.wings.dto.request.admin.events;

import java.time.LocalDate;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.interfaces.HasTranslationMethod;
import lv.wings.enums.TranslationMethod;

@Getter
@Setter
public class NewEventDto implements HasTranslationMethod {
    private TranslationMethod translationMethod;

    // @FutureOrPresent(message = "{start.futureOrPresent}")
    private LocalDate startDate;

    // @Future(message = "{end.future}")
    private LocalDate endDate;

    @NotEmpty(message = "{translations.empty}")
    @Valid
    private List<NewEventTranslationDto> translations;

    private List<MultipartFile> images;
}

