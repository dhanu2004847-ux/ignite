package com.campusconnect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ClaimRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long foundItemId;
    private String claimantName;
    private String claimantContact;
    private String proofDetails;

    public ClaimRequest() {
    }

    public Long getId() {
        return id;
    }

    public Long getFoundItemId() {
        return foundItemId;
    }

    public void setFoundItemId(Long foundItemId) {
        this.foundItemId = foundItemId;
    }

    public String getClaimantName() {
        return claimantName;
    }

    public void setClaimantName(String claimantName) {
        this.claimantName = claimantName;
    }

    public String getClaimantContact() {
        return claimantContact;
    }

    public void setClaimantContact(String claimantContact) {
        this.claimantContact = claimantContact;
    }

    public String getProofDetails() {
        return proofDetails;
    }

    public void setProofDetails(String proofDetails) {
        this.proofDetails = proofDetails;
    }
}