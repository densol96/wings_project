package lv.wings.service;

import lv.wings.dto.request.NewsletterRequestDto;
import lv.wings.dto.response.BasicMessageDto;

public interface NewsletterService {
    BasicMessageDto subscribe(NewsletterRequestDto dto);
}
