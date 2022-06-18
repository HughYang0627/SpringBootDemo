package com.example.SpringBootDemo.Forms;

public interface IFormConvert<S, T> {
	T converFor (S s);
}
