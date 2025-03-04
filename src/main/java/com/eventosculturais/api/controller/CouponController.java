package com.eventosculturais.api.controller;

import com.eventosculturais.api.domain.coupon.Coupon;
import com.eventosculturais.api.domain.coupon.CouponRequestDTO;
import com.eventosculturais.api.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping(value="/event/{eventId}",consumes="multipart/form-data")
    public ResponseEntity<Coupon> addCouponToEvent(@PathVariable UUID eventId,
                                                   @RequestParam("code") String code,
                                                   @RequestParam("valid") Long valid,
                                                   @RequestParam("discount") Integer discount)
                                                   {
        CouponRequestDTO couponRequestDTO = new CouponRequestDTO(code,discount,valid);
        Coupon coupons = this.couponService.addCouponToEvent(eventId,couponRequestDTO);
        return ResponseEntity.ok(coupons);
    }

}
