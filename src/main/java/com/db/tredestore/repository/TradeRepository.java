package com.db.tredestore.repository;

import com.db.tredestore.domain.TradeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<TradeDetails, Long> {

    TradeDetails findBytradeId(String tradeID);
}