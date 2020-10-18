package com.db.tredestore.service;

import com.db.tredestore.domain.TradeDetails;
import com.db.tredestore.domain.TradeRequest;
import com.db.tredestore.repository.TradeRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.db.tredestore.util.FileUtil.buildMockTradeRequest;
import static java.time.LocalDate.of;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceTest {
    @Rule
    public ExpectedException thrown = none();
    private Store store;
    @Mock
    private TradeRepository tradeRepository;
    @Mock
    private TradeService tradeService;
    private TradeDetails tradeDetails;
    @Captor
    private ArgumentCaptor<List<TradeDetails>> tradeDetailsArgumentCaptor;

    @Before
    public void setUp() {
        store = new TradeService(tradeRepository);
        List<TradeRequest> tradeRequests = buildMockTradeRequest("input1.json");
        tradeDetails = new TradeDetails(tradeRequests.get(0));
    }

    @Test
    public void shouldThrowExceptionWhenMaturityDateBeforeToday() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Trade has less Maturity Date then today");
        tradeDetails.setMaturityDate(of(2019, 11, 15));
        store.validateTradeRequest(tradeDetails);
    }

    @Test
    public void shouldThrowExceptionWhenVersionIsSmallerThanPreviousVersion() {
        Mockito.when(tradeRepository.findBytradeId(anyString())).thenReturn(getMockTradeDetails());
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Version is smaller than previous version");
        store.validateTradeRequest(tradeDetails);
    }

    @Test
    public void validateTrade_AllRulesShouldGetValidate() {
        Assertions.assertTrue(store.validateTradeRequest(tradeDetails));
    }

    @Test
    public void saveTrades_ShouldSaveToDataBase() {
        tradeService.saveTrades(asList(getMockTradeDetails()));
        Mockito.verify(tradeService).saveTrades(tradeDetailsArgumentCaptor.capture());
        TradeDetails tradeRequest = tradeDetailsArgumentCaptor.getValue().get(0);
        assertEquals("B1", tradeRequest.getBookId());
        assertEquals("CP-1", tradeRequest.getCounterPartyId());
        assertEquals("T1", tradeRequest.getTradeId());
        assertEquals("2", tradeRequest.getVersion().toString());
        assertEquals("N", tradeRequest.getExpired());
        assertEquals("2020-11-15", tradeRequest.getCreationDate().toString());
        assertEquals("2021-11-15", tradeRequest.getMaturityDate().toString());
    }

    private TradeDetails getMockTradeDetails() {
        TradeDetails tradeDetails = new TradeDetails();
        tradeDetails.setVersion(2);
        tradeDetails.setBookId("B1");
        tradeDetails.setTradeId("T1");
        tradeDetails.setCounterPartyId("CP-1");
        tradeDetails.setExpired("N");
        tradeDetails.setMaturityDate(of(2021, 11, 15));
        tradeDetails.setCreationDate(of(2020, 11, 15));
        return tradeDetails;
    }
}