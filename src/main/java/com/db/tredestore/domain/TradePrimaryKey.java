package com.db.tredestore.domain;

import java.io.Serializable;
import java.util.Objects;

import static java.util.Objects.*;

public class TradePrimaryKey implements Serializable {
    private Integer version;
    private String tradeId;

    public TradePrimaryKey() {
    }

    public TradePrimaryKey(Integer version, String tradeId) {
        this.version = version;
        this.tradeId = tradeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradePrimaryKey that = (TradePrimaryKey) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(tradeId, that.tradeId);
    }

    @Override
    public int hashCode() {
        return hash(version, tradeId);
    }
}
