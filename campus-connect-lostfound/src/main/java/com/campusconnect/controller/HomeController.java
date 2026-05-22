package com.campusconnect.controller;

import com.campusconnect.repository.FoundItemRepository;
import com.campusconnect.repository.LostItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final LostItemRepository lostItemRepository;
    private final FoundItemRepository foundItemRepository;

    public HomeController(LostItemRepository lostItemRepository,
                          FoundItemRepository foundItemRepository) {
        this.lostItemRepository = lostItemRepository;
        this.foundItemRepository = foundItemRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("lostCount", lostItemRepository.findAll().size());
        model.addAttribute("foundCount", foundItemRepository.findAll().size());
        return "index";
    }
}