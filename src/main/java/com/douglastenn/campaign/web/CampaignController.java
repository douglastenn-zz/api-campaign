package com.douglastenn.campaign.web;

import com.douglastenn.campaign.domain.entity.Campaign;
import com.douglastenn.campaign.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/campaign")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Campaign>> index() {
        List<Campaign> campaigns = this.campaignService.findAll();
        return new ResponseEntity<>(campaigns, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Campaign> show(@PathVariable("id") String id) {
        return this.campaignService.findById(id)
                .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Campaign> create(@Valid @RequestBody Campaign campaign) {
        Campaign saved = this.campaignService.save(campaign);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Campaign> update(@PathVariable("id") String id, @Valid @RequestBody Campaign campaign) {
        Campaign updated = this.campaignService.update(id, campaign);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        this.campaignService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
