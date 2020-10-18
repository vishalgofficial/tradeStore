package com.db.tredestore.service;

import com.db.tredestore.domain.TradeDetails;

import java.util.List;

public interface Store {

    boolean validateTradeRequest(TradeDetails tradeDetails);

    void saveTrades(List<TradeDetails> tradeDetails);

}
