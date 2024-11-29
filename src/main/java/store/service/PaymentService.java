package store.service;

import store.repository.StoreRepository;

public class PaymentService {
    private final StoreRepository storeRepository;

    public PaymentService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

}
