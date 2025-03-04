package com.eventosculturais.api.domain.Address;


import com.eventosculturais.api.domain.eventos.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "address")
@Entity
public class Address {

    @Id
    @GeneratedValue
    private UUID id;
    private String city;
    private String uf;


    @OneToOne
    @JoinColumn(name="event_id")
    private Event event;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
