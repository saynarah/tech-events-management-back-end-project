package com.eventosculturais.api.repository;

import com.eventosculturais.api.domain.eventos.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {


    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.address a WHERE e.date >= :currentDate")
    public Page<Event> findUpcomingEvents(@Param("currentDate") Date currentDate, Pageable pageable);


    @Query("SELECT e FROM Event e " +
            "LEFT JOIN e.address a " +
            "WHERE (:title IS NULL OR e.title LIKE %:title%)"
            + "AND (:city IS NULL OR a.city LIKE %:city%)"
            + "AND (:state IS NULL OR a.uf LIKE %:state%)"
            + "AND (e.date >= :startDate "
            + "AND e.date <= :endDate)"
            )
    Page<Event> findFilterEvents(@Param("title") String title,
                                 @Param("city") String city,
                                 @Param("state") String state,
                                 @Param("startDate") Date startDate,
                                 @Param("endDate") Date endDate,
                                 Pageable pageable);
}
