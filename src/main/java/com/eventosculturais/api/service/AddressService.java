package com.eventosculturais.api.service;

import com.eventosculturais.api.domain.Address.Address;
import com.eventosculturais.api.domain.eventos.Event;
import com.eventosculturais.api.domain.eventos.EventRequestDTO;
import com.eventosculturais.api.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(EventRequestDTO data, Event event) {
        Address address = new Address();
        address.setCity(data.city());
        address.setUf(data.state());
        address.setEvent(event);

        return addressRepository.save(address);
    }
}
