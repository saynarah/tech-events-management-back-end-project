package com.eventosculturais.api.service;

import com.eventosculturais.api.domain.coupon.Coupon;
import com.eventosculturais.api.domain.coupon.CouponRequestDTO;
import com.eventosculturais.api.domain.eventos.Event;
import com.eventosculturais.api.repository.CouponRepository;
import com.eventosculturais.api.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CouponService {


    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private EventRepository eventRepository;

    public Coupon addCouponToEvent(UUID eventId, CouponRequestDTO data){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Coupon newCoupon = new Coupon();
        newCoupon.setDiscount(data.discount());
        newCoupon.setValid(new Date(data.valid()));
        newCoupon.setCode(data.code());
        newCoupon.setEvent(event);

        couponRepository.save(newCoupon);

        return newCoupon;
    }

    public List<Coupon> consultarCoupons(UUID eventId, Date date) {
        return couponRepository.findByEventIdAndValidAfter(eventId,date);

    }
}
