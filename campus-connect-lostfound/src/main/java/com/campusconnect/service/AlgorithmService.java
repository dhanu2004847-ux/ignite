package com.campusconnect.service;

import com.campusconnect.model.ClaimRequest;
import com.campusconnect.model.FoundItem;
import com.campusconnect.model.LostItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AlgorithmService {

    public List<String> findMatches(List<LostItem> lostItems, List<FoundItem> foundItems) {
        List<String> matches = new ArrayList<>();

        for (LostItem lost : lostItems) {
            for (FoundItem found : foundItems) {
                int score = 0;

                if (lost.getItemName() != null && found.getItemName() != null
                        && lost.getItemName().equalsIgnoreCase(found.getItemName())) {
                    score += 30;
                }

                if (lost.getCategory() != null && found.getCategory() != null
                        && lost.getCategory().equalsIgnoreCase(found.getCategory())) {
                    score += 30;
                }

                if (lost.getLocation() != null && found.getLocation() != null
                        && lost.getLocation().equalsIgnoreCase(found.getLocation())) {
                    score += 20;
                }

                if (lost.getLostDate() != null && found.getFoundDate() != null
                        && lost.getLostDate().equalsIgnoreCase(found.getFoundDate())) {
                    score += 20;
                }

                if (score >= 60) {
                    matches.add(
                            "Possible Match → Lost Item: " + lost.getItemName()
                                    + " | Found Item: " + found.getItemName()
                                    + " | Score: " + score
                    );
                }
            }
        }

        return matches;
    }

    public int calculatePriority(LostItem item) {
        int score = 0;

        if (item.getCategory() != null) {
            String category = item.getCategory().toLowerCase();

            if (category.contains("id") || category.contains("document")) {
                score += 40;
            }
            if (category.contains("phone") || category.contains("laptop")) {
                score += 35;
            }
            if (category.contains("wallet")) {
                score += 30;
            }
        }

        if (item.getLostDate() != null && !item.getLostDate().isBlank()) {
            score += 20;
        }

        return score;
    }

    public List<LostItem> rankLostItems(List<LostItem> items) {
        items.sort(Comparator.comparingInt(this::calculatePriority).reversed());
        return items;
    }

    public int calculateClaimScore(ClaimRequest claim, FoundItem item) {
        int score = 0;

        String proof = claim.getProofDetails() == null ? "" : claim.getProofDetails().toLowerCase();
        String description = item.getDescription() == null ? "" : item.getDescription().toLowerCase();
        String location = item.getLocation() == null ? "" : item.getLocation().toLowerCase();
        String itemName = item.getItemName() == null ? "" : item.getItemName().toLowerCase();

        if (proof.length() > 15) {
            score += 30;
        }

        if (proof.contains("black") || proof.contains("blue") || proof.contains("red")
                || proof.contains("white") || proof.contains("green")) {
            score += 20;
        }

        if (!location.isBlank() && proof.contains(location)) {
            score += 20;
        }

        if (!itemName.isBlank() && proof.contains(itemName)) {
            score += 15;
        }

        if (!description.isBlank()) {
            String[] words = description.split("\\s+");
            for (String word : words) {
                if (word.length() > 3 && proof.contains(word)) {
                    score += 5;
                    break;
                }
            }
        }

        return Math.min(score, 100);
    }

    public String getClaimConfidence(int score) {
        if (score >= 80) {
            return "HIGH CONFIDENCE";
        } else if (score >= 50) {
            return "MEDIUM CONFIDENCE";
        } else {
            return "LOW CONFIDENCE";
        }
    }
}