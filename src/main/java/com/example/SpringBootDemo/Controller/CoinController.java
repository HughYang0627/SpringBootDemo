package com.example.SpringBootDemo.Controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.SpringBootDemo.Forms.CoinForm;
import com.example.SpringBootDemo.Model.Coin;
import com.example.SpringBootDemo.Service.CoinService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping("/")
public class CoinController {
	
	@Autowired // 取得Service物件
	CoinService coinService;
	
	/**
	 * 查詢
	 * @param Model
	 * @return String
	 */
	@GetMapping("/find")
    public String getCoinList (Model model) {
        Iterable<Coin> coinList = coinService.getCoin();
        model.addAttribute("coinList", coinList);
        Coin coin = new Coin();
        model.addAttribute("coinObject", coin);
        return "coinMain";
    }
	
	/**
	 * 查詢(URL)
	 * @param Model
	 * @return String
	 */
	@GetMapping("/findURL")
    public String getCoinListHttps (Model model) {
		try {
//			String rtnHttps = 
			Map<String, Object> bpiMap = (Map<String, Object>) coinService.httpsDemo().get("bpi");
			Iterable<Coin> coinList = (Iterable<Coin>) httpsMap;
			model.addAttribute("coinList", coinList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        return "coinMain";
    }
	
	/**
	 * 傳遞新增資訊
	 * @param Model
	 * @return String
	 */
	@GetMapping("/addCoin")
    public String addCoin (Model model) {
        model.addAttribute("CoinForm", new CoinForm());
        return "addCoin";
    }
	
	/**
	 * 確定新增
	 * @param CoinForm
	 * @return String
	 */
	@PostMapping("/createCoin")
    public String createCoin(@Validated CoinForm coinForm) {
		Coin coin = coinForm.convertToCoin();
        coinService.createCoin(coin);
        return "redirect:/find";
    }
	
	/**
	 * 傳遞更新資訊
	 * @param Integer
	 * @param Model
	 * @return String
	 */
	@GetMapping("/editCoin/{id}")
    public String editCoin (@PathVariable Integer id, Model model) {
		Coin coin = coinService.findById(id);
        model.addAttribute("coin", coin);
        return "editCoin";
    }
	
	/**
	 * 確定更新
	 * @param Coin
	 * @return String
	 */
	@PostMapping("/editCoinSave")
    public String editCoinSave(Coin coin) {
		coinService.updateCoin(coin.getId() ,coin);
        return "redirect:/find";
    }
	
	/**
	 * 刪除
	 * @param Integer
	 * @return String
	 */
	@GetMapping("/deleteCoin/{id}")
	public String deleteCoin(@PathVariable Integer id) {
		coinService.deleteCoin(id);
		return "redirect:/find";
	}

}
