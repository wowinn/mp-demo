package com.mp.service;

import com.mp.po.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @Test
    void testLogicDeleted() {
        addressService.removeById(59L);

        Address address = addressService.getById(59L);
        System.out.println("address = " + address);
    }

}
