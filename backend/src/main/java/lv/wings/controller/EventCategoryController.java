package lv.wings.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lv.wings.service.EventCategoryService;

@RestController
@RequestMapping("/api/v1/event-categories")
@RequiredArgsConstructor
public class EventCategoryController {

	private final EventCategoryService eventCategoryService;

}
