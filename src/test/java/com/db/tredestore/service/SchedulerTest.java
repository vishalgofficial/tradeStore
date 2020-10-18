package com.db.tredestore.service;

import com.db.tredestore.repository.TradeRepository;
import org.awaitility.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchedulerTest {

    @MockBean
    private TradeRepository tradeRepository;

    @SpyBean
    private Scheduler scheduler;


    @Test
    public void whenWaitOneSecond_thenScheduledIsCalledAtLeastTenTimes() {
        await().atMost(Duration.FIVE_SECONDS)
                .untilAsserted(() -> verify(scheduler, atLeast(1))
                        .updateTradeWhoCrossMaturityDate());
    }

}