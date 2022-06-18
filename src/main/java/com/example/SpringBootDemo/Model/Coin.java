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
	private Integer id;
	
	@Column // 代號
	private String code;
	
	@Column // 符號
	private String symbol;
	
	@Column // 匯率
	private BigDecimal rate;
	
	@Column // 說明
	private String description;
	
	@Column // 浮動利率
	private BigDecimal rate_float;
	
	@Column // 建立日期
	private Date create_date;
	
	@Column // 更新日期
	private Date modify_date;
	
}
