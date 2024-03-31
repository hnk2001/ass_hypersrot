package com.hypersrot.assignment_hypersrot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hypersrot.assignment_hypersrot.service.CouponService;

import java.util.Map;

@RestController
public class CouponController {

    @Autowired
    private CouponService couponService;

    // Endpoint to fetch available coupons
    // @GetMapping("/fetchCoupons")
    // public Map<String, Integer> fetchCoupons() {
    //     return couponService.fetchCoupons();
    // }
}