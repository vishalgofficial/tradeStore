package com.db.tredestore;

import com.db.tredestore.controller.TradeController;
import com.db.tredestore.repository.TradeRepository;
import com.db.tredestore.service.TradeService;
import com.db.tredestore.util.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class FunctionalTest {
    @InjectMocks
    private TradeController tradeController;
    @Mock
    private TradeService tradeService;
    @Mock
    private TradeRepository tradeRepository;


    @Test
    public void executeCases() {
        tradeController.addTrade(FileUtil.buildMockTradeRequest("input1.json"));
    }
}
