package lv.wings.service.impl;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import lv.wings.dto.request.NewsletterRequestDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.exception.other.AlreadySubscribedException;
import lv.wings.model.entity.NewsletterSubscriber;
import lv.wings.repo.NewsletterSubscriberRepository;
import lv.wings.service.LocaleService;
import lv.wings.service.NewsletterService;

@Service
public class NewsletterServiceImpl implements NewsletterService {

    private final NewsletterSubscriberRepository repository;
    private final LocaleService localeService;

    public NewsletterServiceImpl(NewsletterSubscriberRepository repository, LocaleService localeService) {
        this.repository = repository;
        this.localeService = localeService;
    }

    @Override
    public BasicMessageDto subscribe(NewsletterRequestDto dto) {

        /**
         * If no subscriber yet - create and take a locale into cosideration (will determine the language of a notification)
         * 
         * If already exists but was a different locale - update locale.
         * 
         * If already exists and the same locale - return an error.
         */

        return repository.findByEmail(dto.getEmail())
                .map(existingSubscriber -> {
                    if (existingSubscriber.getLocale() == localeService.getCurrentLocaleCode()) {
                        throw new AlreadySubscribedException(
                                "Guest with email of " + dto.getEmail() + " has already subscribed to the newsletter service per same locale...");
                    } else {
                        existingSubscriber.setLocale(localeService.getCurrentLocaleCode());
                        existingSubscriber.setUpdatedAt(LocalDateTime.now());
                        repository.save(existingSubscriber);
                        return new BasicMessageDto(localeService.getMessage("newslettersubscriber.locale-updated"));
                    }
                })
                .orElseGet(() -> {
                    NewsletterSubscriber newSubscriber = NewsletterSubscriber.builder()
                            .email(dto.getEmail())
                            .locale(localeService.getCurrentLocaleCode())
                            .build();

                    repository.save(newSubscriber);
                    return new BasicMessageDto(localeService.getMessage("newslettersubscriber.created"));
                });
    }
}
