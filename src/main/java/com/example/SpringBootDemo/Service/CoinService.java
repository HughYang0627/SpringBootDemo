package com.example.SpringBootDemo.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootDemo.Dao.CoinDao;
import com.example.SpringBootDemo.Model.Coin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CoinService {
	
	@Autowired // 取得Dao物件
	CoinDao coinDao;
	
	/**
	 * 查詢(全部)
	 * @return Iterable<Coin>
	 */
	public Iterable<Coin> getCoin() {
        return coinDao.findAll();
    }
	
	/**
	 * 查詢(by id)
	 * @param Integer
	 * @return Coin
	 */
	public Coin findById(Integer id) {
		Coin coin = coinDao.findById(id).get();
	    return coin;
	}
	
	/**
	 * 新增
	 * @param Coin
	 * @return Iterable<Coin>
	 */
	public Iterable<Coin> createCoin(Coin coin) {
		long row_count = coinDao.count();
		coin.setId((int) row_count + 1);
		coin.setCreate_date(new Date());
        coin.setModify_date(new Date());
        coinDao.save(coin);
        return getCoin();
	}
	
	/**
	 * 更新
	 * @param Integer
	 * @param Coin
	 * @return Coin
	 */
	public Coin updateCoin(Integer id, Coin coin) {
		Coin coinVO = findById(id);
		
		String code = coin.getCode();
		coinVO.setCode(code);
		
		String symbol = coin.getSymbol();
		coinVO.setSymbol(symbol);
		
		BigDecimal rate = coin.getRate();
		coinVO.setRate(rate);
		
		String description = coin.getDescription();
		coinVO.setDescription(description);
		
		BigDecimal rate_float = coin.getRate_float();
		coinVO.setRate_float(rate_float);
		
		coinVO.setModify_date(new Date());
		return coinDao.save(coinVO);
	}

	/**
	 * 刪除
	 * @param Integer
	 * @return Boolean
	 */
	public Boolean deleteCoin(Integer id) {
		try {
			Coin coinVO = findById(id);
			
			if (coinVO != null) {
				coinDao.deleteById(id);
				return true;
			} else {
				return false;
			}
		} catch (Exception exception) {
			return false;
		}
	}
	
	/**
	 * 查詢(URL)
	 * @param Integer
	 * @return Boolean
	 */
	public Map<String,Object> httpsDemo() throws JsonProcessingException {
		String getUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";
		String rtnHttps = "";
		Map<String, Object> result = new HashMap<String,Object>();
		
		try {
			URL url = new URL(getUrl);
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
			httpsURLConnection.connect();
			
			if (httpsURLConnection.getResponseCode() == 200) {
				InputStream inputStream = httpsURLConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				StringBuffer stringBuffer = new StringBuffer();
				String readLine = "";
				while ((readLine = bufferedReader.readLine()) != null) {
					stringBuffer.append(readLine);
				}
				inputStream.close();
				bufferedReader.close();
				httpsURLConnection.disconnect();
				result = new ObjectMapper().readValue(stringBuffer.toString(), HashMap.class);
				rtnHttps = stringBuffer.toString();
			}
		} catch (Exception e) {
			// TODO
		}
		return result ;
	}
	
}
