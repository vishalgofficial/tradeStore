package com.db.tredestore.controller;

import com.db.tredestore.domain.TradeDetails;
import com.db.tredestore.domain.TradeRequest;
import com.db.tredestore.repository.TradeRepository;
import com.db.tredestore.service.TradeService;
import com.db.tredestore.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static org.junit.Assert.assertEquals;
import static org.junit.rules.ExpectedException.none;

@RunWith(MockitoJUnitRunner.class)
public class TradeControllerTest {
    private MockMvc mockMvc;
    @Rule
    public ExpectedException thrown = none();
    @Mock
    private TradeController tradeController;
    @Mock
    private TradeRepository tradeRepository;
    @InjectMocks
    private TradeService tradeService;
    private List<TradeRequest> mockTradeRequests;
    private ObjectMapper mapper;
    @Captor
    private ArgumentCaptor<List<TradeRequest>> tradeRequestArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
        mockTradeRequests = FileUtil.buildMockTradeRequest("input1.json");
    }

    @Test
    public void tradeAddMappingShouldWorkCorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/add")
                .content(mapper.writeValueAsString(mockTradeRequests.get(0)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addTrade_validateTradeRequest() {
        tradeController.addTrade(mockTradeRequests);
        Mockito.verify(tradeController).addTrade(tradeRequestArgumentCaptor.capture());
        TradeRequest tradeRequest = tradeRequestArgumentCaptor.getValue().get(0);
        assertEquals("B1", tradeRequest.getBookId());
        assertEquals("CP-2", tradeRequest.getCounterPartyId());
        assertEquals("T1", tradeRequest.getTradeId());
        assertEquals("1", tradeRequest.getVersion().toString());
        assertEquals("N", tradeRequest.getExpired());
        assertEquals("2020-05-20", tradeRequest.getCreationDate().toString());
        assertEquals("2021-05-10", tradeRequest.getMaturityDate().toString());
    }

    @Test
    public void shouldThrowExceptionWhenNoRecordsToSave() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("No Records to save");
        tradeService.saveTrades(new ArrayList<>());
    }

    private TradeDetails getMockTradeDetails() {
        TradeDetails tradeDetails = new TradeDetails();
        tradeDetails.setVersion(3);
        tradeDetails.setBookId("B1");
        tradeDetails.setTradeId("T1");
        tradeDetails.setCounterPartyId("CP-1");
        tradeDetails.setExpired("N");
        tradeDetails.setMaturityDate(of(2021, 11, 15));
        tradeDetails.setMaturityDate(now());
        return tradeDetails;
    }

}