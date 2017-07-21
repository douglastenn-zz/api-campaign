package com.douglastenn.campaign.web;

import com.douglastenn.campaign.config.JacksonConfig;
import com.douglastenn.campaign.domain.entity.Campaign;
import com.douglastenn.campaign.service.CampaignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Import(JacksonConfig.class)
@WebMvcTest(CampaignController.class)
public class CampaignControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CampaignService campaignService;

    @Test
    public void index_WithValidVerb() throws Exception {

        given(this.campaignService.findAll())
                .willReturn(asList(
                        new Campaign("1", LocalDate.now(), LocalDate.now(), 1L),
                        new Campaign("2", LocalDate.now(), LocalDate.now(), 2L)
                ));

        this.mvc.perform(get("/api/campaign").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"));
    }

    @Test
    public void show_existingId() throws Exception {
        given(this.campaignService.findById("1"))
                .willReturn(Optional.of(new Campaign("1", LocalDate.now(), LocalDate.now(), 1L)));

        this.mvc.perform(get("/api/campaign/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    public void show_nonExistingId() throws Exception {
        given(this.campaignService.findById("1"))
                .willReturn(Optional.empty());

        this.mvc.perform(get("/api/campaign/{id}", "1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void create() throws Exception {
        given(this.campaignService.save(any(Campaign.class)))
                .willReturn(new Campaign("1", LocalDate.now(), LocalDate.now(), 1L));

        this.mvc.perform(post("/api/campaign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Campaign("1", LocalDate.now(), LocalDate.now(), 1L))))
                .andExpect(status().isCreated());
    }

    @Test
    public void update() throws Exception {
        given(this.campaignService.update("1", new Campaign("1", LocalDate.now(), LocalDate.of(2017, 12, 31), 1L)))
                .willReturn(new Campaign("1", LocalDate.now(), LocalDate.of(2017, 12, 31), 1L));

        mvc.perform(put("/api/campaign/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Campaign("1", LocalDate.now(), LocalDate.of(2017, 12, 31), 1L))))

                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2017-12-31"));
    }

}