package com.db.tredestore.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

import static java.time.LocalDate.*;

@Entity
@Getter
@Setter
@DynamicUpdate
@Table(name = "TRADE_DETAILS")
@IdClass(TradePrimaryKey.class)
public class TradeDetails {

    @Id
    @Column(name = "VERSION")
    private Integer version;

    @Id
    @Column(name = "TRADE_ID")
    private String tradeId;

    @Column(name = "COUNTER_ID")
    private String counterPartyId;

    @Column(name = "BOOK_ID")
    private String bookId;

    @Column(name = "MATURITY_DATE")
    private LocalDate maturityDate;

    @Column(name = "CREATION_DATE")
    private LocalDate creationDate;

    @Column(name = "EXPIRED")
    private String expired;

    public TradeDetails() {
    }

    public TradeDetails(TradeRequest tradeRequest) {
        this.version = tradeRequest.getVersion();
        this.tradeId = tradeRequest.getTradeId();
        this.counterPartyId = tradeRequest.getCounterPartyId();
        this.bookId = tradeRequest.getBookId();
        this.maturityDate = tradeRequest.getMaturityDate();
        this.creationDate = tradeRequest.getCreationDate() == null ? now() : tradeRequest.getCreationDate();
        this.expired = tradeRequest.getExpired();
    }

    public TradeDetails(TradeDetails tradeDetails) {
        this.version = tradeDetails.version;
        this.tradeId = tradeDetails.tradeId;
        this.counterPartyId = tradeDetails.counterPartyId;
        this.bookId = tradeDetails.bookId;
        this.maturityDate = tradeDetails.maturityDate;
        this.creationDate = tradeDetails.creationDate;
        this.expired = tradeDetails.expired;
    }

}
