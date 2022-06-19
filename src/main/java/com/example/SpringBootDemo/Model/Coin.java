package com.example.SpringBootDemo.Model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import lombok.*;

@Entity
@Table
@Data
@ToString
public class Coin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", length = 32, unique = true, nullable = false)
	private Integer id;
	
	// 代號
	@Column(name = "CODE", length = 10, nullable = false)
	private String code;
	
	// 符號
	@Column(name = "SYMBOL", length = 20)
	private String symbol;
	
	// 匯率
	@Column(name = "RATE", precision = 17, scale = 4)
	private BigDecimal rate;
	
	// 說明
	@Column(name = "DESCRIPTION", length = 200)
	private String description;
	
	// 浮動利率
	@Column(name = "RATE_FLOAT", precision = 17, scale = 4)
	private BigDecimal rate_float;
	
	// 建立日期
	@Column(name = "CREATE_DATE")
	private Date create_date;
	
	// 更新日期
	@Column(name = "MODIFY_DATE")
	private Date modify_date;
	
}
