package com.eventosculturais.api.repository;

import com.eventosculturais.api.domain.Address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
