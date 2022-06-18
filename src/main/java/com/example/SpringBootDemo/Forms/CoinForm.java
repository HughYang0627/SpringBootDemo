package com.example.SpringBootDemo.Forms;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.BeanUtils;

import com.example.SpringBootDemo.Model.Coin;

import lombok.Data;

@Data
public class CoinForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String code;
	
	private String symbol;
	
	private BigDecimal rate;
	
	private String description;
	
	private BigDecimal rate_float;
	
	public Coin convertToCoin() {
		Coin coin = new coinFormConvert().converFor(this);
		return coin;
	}
	
	private static class coinFormConvert implements IFormConvert<CoinForm, Coin> {
		
		@Override
		public Coin converFor(CoinForm s) {
			Coin coin = new Coin();
			BeanUtils.copyProperties(s, coin);
			return coin;
		}
	}

}
