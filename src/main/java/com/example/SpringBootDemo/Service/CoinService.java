package com.example.SpringBootDemo.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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
	@SuppressWarnings("unchecked")
	public List<Coin> getCoinListByURL() throws JsonProcessingException {
		String getUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";
		Map<String, Object> result = new HashMap<String,Object>();
		List<Coin> coinList = new ArrayList<Coin>();
		
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
				
				Map<String, Object> bpiMap = new HashMap<String, Object>();
				bpiMap = result.get("bpi") != null ? (Map<String, Object>) result.get("bpi") : null;
				
				Set<Entry<String, Object>> setEntry = null;
				for (Map.Entry<String, Object> entryCoin : bpiMap.entrySet()) {
					System.out.println("Key = " + entryCoin.getKey() + ", Value = " + entryCoin.getValue());
					setEntry = bpiMap.entrySet();
				}
				Map<String, Object> coinMapTemp = new HashMap<String, Object>();
				Map<String, Object> coinMap = new HashMap<String, Object>();
				for (Entry<String, Object> entry : setEntry) {
					coinMapTemp.put(entry.getKey(), entry.getValue());
					coinMap = coinMapTemp.get(entry.getKey()) != null ? (Map<String, Object>) coinMapTemp.get(entry.getKey()) : null;
					Coin coin = new Coin();
					if (coinMap != null) {
						// 代號
						String code = coinMap.get("code") != null ? coinMap.get("code").toString() : null;
						coin.setCode(code);
						
						// 符號
						String symbol = coinMap.get("symbol") != null ? coinMap.get("symbol").toString() : null;
						coin.setSymbol(symbol);
						
						// 匯率
						String rateStr = coinMap.get("rate") != null ? coinMap.get("rate").toString().replace(",", "") : "0";
						BigDecimal rate = new BigDecimal(rateStr);
						coin.setRate(rate);
						
						// 說明
						String description = coinMap.get("description") != null ? coinMap.get("description").toString() : null;
						coin.setDescription(description);
						
						// 利率
						String rate_floatStr = coinMap.get("rate_float") != null ? coinMap.get("rate_float").toString().replace(",", "") : "0";
						BigDecimal rate_float = new BigDecimal(rate_floatStr);
						coin.setRate_float(rate_float);
						coinList.add(coin);
					}
				}
			}
		} catch (Exception e) {
			// TODO
		}
		return coinList;
	}
	
	/**
	 * 查詢(URL)
	 * @param Integer
	 * @return Boolean
	 */
	@SuppressWarnings("unchecked")
	public List<Coin> editURL(Coin coin) throws JsonProcessingException {
		String getUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";
		Map<String, Object> result = new HashMap<String,Object>();
		List<Coin> coinList = new ArrayList<Coin>();
		
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
				
				Map<String, Object> bpiMap = new HashMap<String, Object>();
				bpiMap = result.get("bpi") != null ? (Map<String, Object>) result.get("bpi") : null;
				
				Set<Entry<String, Object>> setEntry = null;
				for (Map.Entry<String, Object> entryCoin : bpiMap.entrySet()) {
					System.out.println("Key = " + entryCoin.getKey() + ", Value = " + entryCoin.getValue());
					setEntry = bpiMap.entrySet();
				}
				Map<String, Object> coinMapTemp = new HashMap<String, Object>();
				Map<String, Object> coinMap = new HashMap<String, Object>();
				for (Entry<String, Object> entry : setEntry) {
					coinMapTemp.put(entry.getKey(), entry.getValue());
					coinMap = coinMapTemp.get(entry.getKey()) != null ? (Map<String, Object>) coinMapTemp.get(entry.getKey()) : null;
					Coin coinVO = new Coin();
					if (coinMap != null) {
						// 代號
						String code = coinMap.get("code") != null ? coinMap.get("code").toString() : null;
						coinVO.setCode(code);
						
						// 符號
						String symbol = coinMap.get("symbol") != null ? coinMap.get("symbol").toString() : null;
						coinVO.setSymbol(symbol);
						
						// 匯率
						String rateStr = coinMap.get("rate") != null ? coinMap.get("rate").toString().replace(",", "") : "0";
						BigDecimal rate = new BigDecimal(rateStr);
						coinVO.setRate(rate);
						
						// 說明
						String description = coinMap.get("description") != null ? coinMap.get("description").toString() : null;
						coinVO.setDescription(description);
						
						// 利率
						String rate_floatStr = coinMap.get("rate_float") != null ? coinMap.get("rate_float").toString().replace(",", "") : "0";
						BigDecimal rate_float = new BigDecimal(rate_floatStr);
						coinVO.setRate_float(rate_float);
					}
					
					if (coinVO.getCode().equals(coin.getCode())) {
						coinVO.setCode(coin.getCode());
						coinVO.setSymbol(coin.getSymbol());
						coinVO.setRate(coin.getRate());
						coinVO.setDescription(coin.getDescription());
						coinVO.setRate_float(coin.getRate_float());
						coinVO.setModify_date(new Date());
						coinList.add(coinVO);
					}
				}
			}
		} catch (Exception e) {
			// TODO
		}
		return coinList;
	}
	
}
