package com.exchange.money.Controller;

import java.util.HashMap;
import java.util.Map;

import com.exchange.money.Service.ExchangeService;
import com.exchange.money.Utils.CustomException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeController {

    @Autowired
    ExchangeService exchangeService;

    @GetMapping("/exchangeRates")
    @ResponseBody
    public String exchangeRates(){
        return exchangeService.getExchangeRate();
    }

    @GetMapping("/calculation")
    @ResponseBody
    public HashMap<String, Float> calculation(@RequestParam Float calData, @RequestParam Float targetNum){
        return exchangeService.getCalculationData(calData, targetNum);
    }

    @ExceptionHandler(value = { CustomException.class })
    @ResponseBody
    public Map<String, String> handleException(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("errMsg", e.getMessage());
        return map;
    }
    
}
