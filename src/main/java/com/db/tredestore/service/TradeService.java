package com.db.tredestore.service;

import com.db.tredestore.domain.TradeDetails;
import com.db.tredestore.repository.TradeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.now;

@Service
@AllArgsConstructor
public class TradeService implements Store {

    private TradeRepository tradeRepository;

    @Override
    public boolean validateTradeRequest(TradeDetails tradeDetails) {
        return validateMaturityDate(tradeDetails.getMaturityDate()) && validateVersionOfTrade(tradeDetails);
    }

    @Override
    public void saveTrades(List<TradeDetails> tradeDetails) {
        if (tradeDetails.isEmpty()) {
            throw new RuntimeException("No Records to save");
        } else {
            tradeRepository.saveAll(tradeDetails);
        }
    }

    private boolean validateMaturityDate(LocalDate maturityDate) {
        if (maturityDate.isBefore(now())) {
            throw new RuntimeException("Trade has less Maturity Date then today");
        }
        return true;
    }

    private boolean validateVersionOfTrade(TradeDetails tradeDetail) {
        TradeDetails details = tradeRepository.findBytradeId(tradeDetail.getTradeId());
        if (details != null) {
            if (tradeDetail.getVersion() < details.getVersion()) {
                throw new RuntimeException("Version is smaller than previous version");
            }
        }
        return true;
    }

}
