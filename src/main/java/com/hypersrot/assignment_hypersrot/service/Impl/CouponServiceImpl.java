package com.hypersrot.assignment_hypersrot.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hypersrot.assignment_hypersrot.model.Coupon;
import com.hypersrot.assignment_hypersrot.repository.CouponRepository;
import com.hypersrot.assignment_hypersrot.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService{

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

}
