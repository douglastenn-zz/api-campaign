package com.douglastenn.campaign.service;

import com.douglastenn.campaign.domain.entity.Campaign;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CampaignService {

    List<Campaign> findAll();

    Campaign save(Campaign campaign);

    Campaign update(String id, Campaign campaign);

    void delete(String id);

    Campaign findById(String id);
}
