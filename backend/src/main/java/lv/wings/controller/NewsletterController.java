package lv.wings.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lv.wings.dto.request.NewsletterRequestDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.model.Test;
import lv.wings.model.entity.NewsletterSubscriber;
import lv.wings.repo.TestRepository;
import lv.wings.service.impl.NewsletterServiceImpl;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/newsletter")
public class NewsletterController {

    private final NewsletterServiceImpl newsletterService;

    public NewsletterController(NewsletterServiceImpl newsletterService) {
        this.newsletterService = newsletterService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<BasicMessageDto> subscribe(@Valid @RequestBody NewsletterRequestDto request) {
        return ResponseEntity.ok().body(newsletterService.subscribe(request));
    }
}
