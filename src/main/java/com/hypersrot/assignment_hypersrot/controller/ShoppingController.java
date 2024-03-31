package com.hypersrot.assignment_hypersrot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hypersrot.assignment_hypersrot.model.Coupon;
import com.hypersrot.assignment_hypersrot.service.CouponService;

@RestController
public class ShoppingController {

    @Autowired
    CouponService couponService;

    @GetMapping("/fetch-Coupons")
    public ResponseEntity<List<Coupon>> getCoupons(){
    List<Coupon> c = couponService.getAllCoupons();
    return new ResponseEntity<List<Coupon>>(c, HttpStatus.OK);
    }
}
