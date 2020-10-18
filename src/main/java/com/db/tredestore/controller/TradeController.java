package com.db.tredestore.controller;

import com.db.tredestore.domain.TradeDetails;
import com.db.tredestore.domain.TradeRequest;
import com.db.tredestore.service.TradeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
public class TradeController {

    private TradeService tradeService;

    //Can use ReadyAPI OR Postman to hit this endpoint sample input i.e input.json file added resource folder
    @PostMapping("/trade/add")
    public ResponseEntity addTrade(@RequestBody List<TradeRequest> tradeRequests) {
        List<TradeDetails> tradeDetails = tradeRequests
                .stream()
                .map(TradeDetails::new)
                .filter(tradeDetail -> tradeService.validateTradeRequest(tradeDetail))
                .collect(toList());
        tradeService.saveTrades(tradeDetails);
        return new ResponseEntity(OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> handleExceptions(Exception e) {
        return new ResponseEntity<>(e, NOT_ACCEPTABLE);
    }
}
