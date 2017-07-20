package com.douglastenn.campaign.service.impl;

import com.douglastenn.campaign.domain.CampaignRepository;
import com.douglastenn.campaign.domain.entity.Campaign;
import com.douglastenn.campaign.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private  CampaignRepository campaignRepository;

    @Override
    public List<Campaign> findAll() {
        return this.campaignRepository.findByEndDateGreaterThanEqual(LocalDate.now());
    }

    @Override
    public Campaign findById(String id) {
        return this.campaignRepository.findById(id);
    }

    @Override
    public Campaign save(Campaign campaign) {
        final List<Campaign> matchedCampaigns = this.campaignRepository.findByStartDateGreaterThanEqualAndEndDateIsLessThanEqual(campaign.startDate, campaign.endDate);

        final List<Campaign> updatedCampaigns = matchedCampaigns
                .stream()
                .map(c -> {
                    c.setEndDate(c.getEndDate().plusDays(1));
                    c.setUpdatedAt(LocalDate.now());
                    this.increaseCampaign(campaign, matchedCampaigns);
                    return c;
                }).collect(toList());

        this.campaignRepository.save(updatedCampaigns);

        return campaignRepository.save(campaign);
    }

    @Override
    public Campaign update(String id, Campaign requestedCampaign) {
        final Campaign campaign = this.campaignRepository.findById(id);

        campaign.setStartDate(requestedCampaign.startDate);
        campaign.setEndDate(requestedCampaign.endDate);
        campaign.setTeamId(requestedCampaign.teamId);

        return this.save(campaign);
    }

    @Override
    public void delete(String id) {
        this.campaignRepository.deleteById(id);
    }

    private void increaseCampaign(Campaign campaign, List<Campaign> campaigns) {
        campaigns
            .stream()
            .filter(c -> !c.equals(campaign) && c.getEndDate().equals(campaign.getEndDate()))
            .forEach(c -> {
                c.setEndDate(c.getEndDate().plusDays(1));
                c.setUpdatedAt(LocalDate.now());
                this.increaseCampaign(c, campaigns);
            });
    }
}
