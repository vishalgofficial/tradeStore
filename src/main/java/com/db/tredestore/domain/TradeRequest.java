package com.db.tredestore.domain;

import com.db.tredestore.util.CustomLocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class TradeRequest implements Serializable {

    private String tradeId;
    private Integer version;
    private String counterPartyId;
    private String bookId;
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate maturityDate;
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate creationDate;
    private String expired;

    public TradeRequest() {
    }
}
