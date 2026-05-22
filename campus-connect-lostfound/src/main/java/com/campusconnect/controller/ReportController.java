package com.campusconnect.controller;

import com.campusconnect.model.FoundItem;
import com.campusconnect.model.LostItem;
import com.campusconnect.repository.FoundItemRepository;
import com.campusconnect.repository.LostItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReportController {

    private final LostItemRepository lostItemRepository;
    private final FoundItemRepository foundItemRepository;

    public ReportController(LostItemRepository lostItemRepository,
                            FoundItemRepository foundItemRepository) {
        this.lostItemRepository = lostItemRepository;
        this.foundItemRepository = foundItemRepository;
    }

    @GetMapping("/report-lost")
    public String reportLostForm(Model model) {
        model.addAttribute("lostItem", new LostItem());
        return "report-lost";
    }

    @PostMapping("/report-lost")
    public String submitLost(@ModelAttribute LostItem lostItem, Model model) {
        lostItemRepository.save(lostItem);
        model.addAttribute("successMessage", "Lost item reported successfully.");
        model.addAttribute("lostItem", new LostItem());
        return "report-lost";
    }

    @GetMapping("/report-found")
    public String reportFoundForm(Model model) {
        model.addAttribute("foundItem", new FoundItem());
        return "report-found";
    }

    @PostMapping("/report-found")
    public String submitFound(@ModelAttribute FoundItem foundItem, Model model) {
        foundItemRepository.save(foundItem);
        model.addAttribute("successMessage", "Found item reported successfully.");
        model.addAttribute("foundItem", new FoundItem());
        return "report-found";
    }
}