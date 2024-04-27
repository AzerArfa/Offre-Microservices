package com.offer.services.admin;

import com.offer.entity.Offer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    Offer createOffer(Offer offer);

    List<Offer> getAllOffers();

    Offer updateOffer(Offer offer);

    void deleteOffer(Long id);
}
