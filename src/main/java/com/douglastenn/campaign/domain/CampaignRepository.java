package com.douglastenn.campaign.domain;

import com.douglastenn.campaign.domain.entity.Campaign;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface CampaignRepository extends MongoRepository<Campaign, String> {

    List<Campaign> findByEndDateGreaterThanEqual(LocalDate now);

    List<Campaign> findByStartDateGreaterThanEqualAndEndDateIsLessThanEqual(LocalDate startDate, LocalDate endDate);

    Campaign findById(String id);

    void deleteById(String id);
}
