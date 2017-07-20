package com.douglastenn.campaign.service.impl;

import com.douglastenn.campaign.domain.CampaignRepository;
import com.douglastenn.campaign.domain.entity.Campaign;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CampaignServiceImplTest {

    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks
    private CampaignServiceImpl campaignService;

    @Captor
    private ArgumentCaptor<List<Campaign>> campaingCaptor;

    @Test
    public void save_GivenNewCampaignThatMatchedWithExists_IncreaseExistingCampaignDate() {

        //given
        Campaign campaign = new Campaign(
                LocalDate.of(2017, 10, 01),
                LocalDate.of(2017, 10, 03),
                1L);

        final List<Campaign> updatedCampaigns = Arrays.asList(
                new Campaign(
                        "01",
                        LocalDate.of(2017, 10, 01),
                        LocalDate.of(2017, 10, 03),
                        1L),
                new Campaign(
                        "02",
                        LocalDate.of(2017, 10, 01),
                        LocalDate.of(2017, 10, 02),
                        1L));

        when(campaignRepository.findByStartDateGreaterThanEqualAndEndDateIsLessThanEqual(campaign.startDate, campaign.getEndDate()))
                .thenReturn(updatedCampaigns);

        when(campaignRepository.save(campaign))
                .thenReturn(campaign);

        when(campaignRepository.save(campaingCaptor.capture())).thenReturn(updatedCampaigns);

        //when
        campaignService.save(campaign);
        
        //then
        final List<Campaign> capturedValues = campaingCaptor.getValue();

        assertThat(capturedValues.size())
                .isEqualTo(2);

        final Optional<Campaign> campaign1 = capturedValues
                .stream()
                .filter(value -> value.getId().equals("01"))
                .findFirst();

        assertThat(campaign1.isPresent())
                .isTrue();
        assertThat(campaign1.get().getEndDate())
                .isEqualTo(LocalDate.of(2017, 10, 5));


        Mockito.verify(campaignRepository, times(1)).save(campaign);
        Mockito.verify(campaignRepository, times(1)).save(updatedCampaigns);

    }

}