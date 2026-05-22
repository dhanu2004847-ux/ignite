package com.campusconnect.controller;

import com.campusconnect.model.ClaimRequest;
import com.campusconnect.model.FoundItem;
import com.campusconnect.model.LostItem;
import com.campusconnect.repository.ClaimRepository;
import com.campusconnect.repository.FoundItemRepository;
import com.campusconnect.repository.LostItemRepository;
import com.campusconnect.service.AlgorithmService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    private final LostItemRepository lostItemRepository;
    private final FoundItemRepository foundItemRepository;
    private final ClaimRepository claimRepository;
    private final AlgorithmService algorithmService;

    public AdminController(LostItemRepository lostItemRepository,
                           FoundItemRepository foundItemRepository,
                           ClaimRepository claimRepository,
                           AlgorithmService algorithmService) {
        this.lostItemRepository = lostItemRepository;
        this.foundItemRepository = foundItemRepository;
        this.claimRepository = claimRepository;
        this.algorithmService = algorithmService;
    }

    @GetMapping("/admin-history")
    public String adminHistory(Model model) {
        List<LostItem> lostItems = lostItemRepository.findAll();
        List<FoundItem> foundItems = foundItemRepository.findAll();
        List<ClaimRequest> claims = claimRepository.findAll();

        model.addAttribute("lostItems", lostItems);
        model.addAttribute("foundItems", foundItems);
        model.addAttribute("claims", claims);

        model.addAttribute("lostCount", lostItems.size());
        model.addAttribute("foundCount", foundItems.size());
        model.addAttribute("claimCount", claims.size());

        return "admin-history";
    }

    @GetMapping("/matches")
    public String matches(Model model) {
        List<LostItem> lostItems = lostItemRepository.findAll();
        List<FoundItem> foundItems = foundItemRepository.findAll();

        List<String> matches = algorithmService.findMatches(lostItems, foundItems);
        model.addAttribute("matches", matches);

        return "matches";
    }

    @GetMapping("/priority-ranking")
    public String priorityRanking(Model model) {
        List<LostItem> rankedItems =
                algorithmService.rankLostItems(new ArrayList<>(lostItemRepository.findAll()));

        model.addAttribute("rankedItems", rankedItems);
        model.addAttribute("algorithmService", algorithmService);

        return "priority-ranking";
    }

    @GetMapping("/claim-validations")
    public String claimValidations(Model model) {
        List<ClaimRequest> claims = claimRepository.findAll();
        List<FoundItem> foundItems = foundItemRepository.findAll();

        List<String> validationResults = new ArrayList<>();

        for (ClaimRequest claim : claims) {
            FoundItem matchedItem = null;

            for (FoundItem item : foundItems) {
                if (item.getId() != null && item.getId().equals(claim.getFoundItemId())) {
                    matchedItem = item;
                    break;
                }
            }

            if (matchedItem != null) {
                int score = algorithmService.calculateClaimScore(claim, matchedItem);
                String confidence = algorithmService.getClaimConfidence(score);

                validationResults.add(
                        "Claim ID: " + claim.getId()
                                + " | Claimant: " + claim.getClaimantName()
                                + " | Item: " + matchedItem.getItemName()
                                + " | Score: " + score
                                + " | Result: " + confidence
                );
            } else {
                validationResults.add(
                        "Claim ID: " + claim.getId()
                                + " | Claimant: " + claim.getClaimantName()
                                + " | Result: Item not found"
                );
            }
        }

        model.addAttribute("validationResults", validationResults);
        return "claim-validations";
    }
}