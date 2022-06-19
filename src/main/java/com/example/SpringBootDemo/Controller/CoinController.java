package com.example.SpringBootDemo.Controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.SpringBootDemo.Forms.CoinForm;
import com.example.SpringBootDemo.Model.Coin;
import com.example.SpringBootDemo.Service.CoinService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
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
        return "coinMain";
    }
	
	/**
	 * 查詢(URL)
	 * @param String
	 * @param Model
	 * @return String
	 * @throws Exception
	 */
//	@RequestMapping(value = "/findURL/{flag}")
	@GetMapping("/findURL")
    public String getCoinListByURL (Model model) throws Exception {
		Iterable<Coin> coinList = coinService.getCoinListByURL();
		model.addAttribute("coinList", coinList);
        return "queryURL";
    }
	
	/**
	 * 傳遞更新(URL)資訊
	 * @param String
	 * @param Model
	 * @return String
	 */
	@GetMapping("/editURL/{code}/{symbol}/{rate}/{description}/{rate_float}")
    public String editURL (@PathVariable String code, @PathVariable String symbol, 
    		@PathVariable String rate, @PathVariable String description, @PathVariable String rate_float, Model model) {
		Coin coinVO = new Coin();
		coinVO.setCode(code);
		coinVO.setSymbol(symbol);
		
		rate = rate != null && !"".equals(rate) ? rate.replace(",", "") : "0";
		BigDecimal rateFormat = new BigDecimal(rate);
		coinVO.setRate(rateFormat);
		coinVO.setDescription(description);
		
		rate_float = rate_float != null && !"".equals(rate_float) ? rate_float.replace(",", "") : "0";
		BigDecimal rate_floatFormat = new BigDecimal(rate_float);
		coinVO.setRate_float(rate_floatFormat);
        model.addAttribute("coin", coinVO);
        return "editURL";
    }
	
	/**
	 * 確定更新(URL)
	 * @param Model
	 * @param Coin
	 * @return String
	 * @throws JsonProcessingException 
	 */
	@PostMapping("/saveURL")
    public String editURL(Model model, Coin coin) throws JsonProcessingException {
		Iterable<Coin> coinList = coinService.editURL(coin);
		model.addAttribute("coinList", coinList);
        return "queryURL";
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
