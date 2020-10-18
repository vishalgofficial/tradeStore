package com.db.tredestore.service;

import com.db.tredestore.domain.TradeDetails;
import com.db.tredestore.repository.TradeRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.now;

@AllArgsConstructor
@Service
public class Scheduler {
    private TradeRepository tradeRepository;

    @Scheduled(cron = "${spring.schedule.time}")
    public void updateTradeWhoCrossMaturityDate() {
        System.out.println("Scheduled method called....");
        List<TradeDetails> tradeOrders = new ArrayList<>();
        List<TradeDetails> tradeDetailsList = tradeRepository.findAll();
        tradeDetailsList.stream()
                .filter(Scheduler::checkMaturityDateIsBeforeTodayOrNot)
                .map(TradeDetails::new)
                .forEach(details -> {
                    details.setExpired("Y");
                    tradeOrders.add(details);
                });
        saveRecordsWhichAreEligibleForUpdate(tradeOrders);
    }

    private static boolean checkMaturityDateIsBeforeTodayOrNot(TradeDetails tradeDetail) {
        return tradeDetail.getMaturityDate().isBefore(now());
    }

    private void saveRecordsWhichAreEligibleForUpdate(List<TradeDetails> tradeOrders) {
        if (tradeOrders.isEmpty()) {
            System.out.println("No Records found for update");
        } else {
            tradeRepository.saveAll(tradeOrders);
        }
    }
}
