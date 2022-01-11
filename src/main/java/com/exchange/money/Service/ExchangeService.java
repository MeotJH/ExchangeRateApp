package com.exchange.money.Service;

import java.io.IOException;
import java.util.HashMap;

import com.exchange.money.Utils.CustomException;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService {

    private String exchangeUrl = "http://api.currencylayer.com/live?access_key=037622d14be0efc0edbc5c1075f41bfa";

    public String getExchangeRate(){

        String result = "";
        try {

            CloseableHttpClient hc = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(exchangeUrl);
            httpGet.addHeader("Content-Type", "application/json");
            CloseableHttpResponse httpResponse = hc.execute(httpGet);
            result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    public HashMap<String, Float> getCalculationData(float calData, float targetNum){

        if (calData > 10000 || calData <= 0) {
            throw new CustomException("송금액이 바르지 않습니다.");
        }

        if( targetNum == 0 ){
            throw new CustomException("환율을 넣어주세요.");
        }

        float calculationData = calData * targetNum;
        HashMap<String, Float> resultData = new HashMap<String, Float>();
        resultData.put("calculationData", calculationData);

        return resultData;

    }
    
}
