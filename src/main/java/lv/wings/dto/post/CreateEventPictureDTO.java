package lv.wings.dto.post;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class CreateEventPictureDTO {

    //private String referenceToPicture;

    private String title;

    //private String description;

    @NotNull
    private int eventId;
}


