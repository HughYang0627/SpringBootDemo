package com.example.SpringBootDemo.Dao;

import org.springframework.data.repository.CrudRepository;

import com.example.SpringBootDemo.Model.Coin;

public interface CoinDao extends CrudRepository<Coin, Integer> {

}

