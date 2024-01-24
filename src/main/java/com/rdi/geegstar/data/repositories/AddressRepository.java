package com.rdi.geegstar.data.repositories;

import com.rdi.geegstar.data.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
