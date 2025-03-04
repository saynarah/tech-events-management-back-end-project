package com.eventosculturais.api.domain.coupon;


import java.util.Date;

public record CouponRequestDTO (String code,
                                Integer discount,
                                Long valid){

}