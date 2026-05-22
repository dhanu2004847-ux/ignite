package com.campusconnect.controller;

import com.campusconnect.model.ClaimRequest;
import com.campusconnect.repository.ClaimRepository;
import com.campusconnect.repository.FoundItemRepository;
import com.campusconnect.repository.LostItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ItemController {

    private final LostItemRepository lostItemRepository;
    private final FoundItemRepository foundItemRepository;
    private final ClaimRepository claimRepository;

    public ItemController(LostItemRepository lostItemRepository,
                          FoundItemRepository foundItemRepository,
                          ClaimRepository claimRepository) {
        this.lostItemRepository = lostItemRepository;
        this.foundItemRepository = foundItemRepository;
        this.claimRepository = claimRepository;
    }

    @GetMapping("/lost-items")
    public String lostItems(Model model) {
        model.addAttribute("lostItems", lostItemRepository.findAll());
        return "lost-items";
    }

    @GetMapping("/found-items")
    public String foundItems(Model model) {
        model.addAttribute("foundItems", foundItemRepository.findAll());
        return "found-items";
    }

    @GetMapping("/claim-item")
    public String claimItemPage(Model model) {
        model.addAttribute("claimRequest", new ClaimRequest());
        model.addAttribute("foundItems", foundItemRepository.findAll());
        return "claim-item";
    }

    @PostMapping("/claim-item")
    public String submitClaim(@ModelAttribute ClaimRequest claimRequest, Model model) {
        claimRepository.save(claimRequest);
        model.addAttribute("successMessage", "Claim request submitted successfully.");
        model.addAttribute("claimRequest", new ClaimRequest());
        model.addAttribute("foundItems", foundItemRepository.findAll());
        return "claim-item";
    }
}