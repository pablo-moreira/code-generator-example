package com.github.cg.example.jsf.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class MaskCtrl {

	public String getLicensePlate() {
		return "aaa-9999";
	}
}
