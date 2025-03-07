package lv.wings.old_dtos.post;


import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lv.wings.model.EventPicture;


@Setter
@Getter
@ToString
public class EventDTO {

    private int id;

    @NotNull
    @Size(min = 5, max = 300, message = "Nosaukums nedrīkst saturēt mazāk par 5 vai vairāk par 300 rakstzīmēm!")
    private String title;

    @NotNull
    @Size(min = 2, max = 200, message = "Vieta nedrīkst saturēt mazāk par 2 vai vairāk par 200 rakstzīmēm!")
    private String location;

    @NotNull
    @Size(min = 0, max = 3000, message = "Aprakstā par daudz rakstzīmju! (0-3000)")
    private String description;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date startDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date endDate;

    private String keyWords;
    
    @NotNull
    private int eventCategoryId;


    private Collection<GetEventPictureDTO> eventPictures;
}
