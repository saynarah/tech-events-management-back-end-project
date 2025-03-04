package com.eventosculturais.api.repository;

import com.eventosculturais.api.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {


   @Query("SELECT c FROM Coupon c WHERE c.valid >= :currentDate AND c.event.id = :eventId")
   List<Coupon> findValidCouponsByEventId(@Param("eventId") UUID eventId,
                                           @Param("currentDate") Date currentDate);

    List<Coupon> findByEventIdAndValidAfter(UUID eventId, Date currentDate);


}
