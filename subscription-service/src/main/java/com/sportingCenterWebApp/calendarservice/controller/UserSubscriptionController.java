package com.sportingCenterWebApp.calendarservice.controller;

import com.sportingCenterWebApp.calendarservice.model.Subscription;
import com.sportingCenterWebApp.calendarservice.repo.SubscriptionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserSubscriptionController {

    private final SubscriptionRepository subscriptionRepository;

    public UserSubscriptionController(SubscriptionRepository abbRepository) {
        this.subscriptionRepository = abbRepository;
    }

    @GetMapping("/subscriptions")
    public List<Subscription> getSubscriptions() {
        return (List<Subscription>) subscriptionRepository.findAll();
    }

    @RequestMapping(value = "/getSubfromid/{subId}", method = RequestMethod.GET)
    public Subscription getSubscriptionById(@PathVariable("subId") Long id) {
        Optional<Subscription> subOp = subscriptionRepository.findById(id);
        Subscription subscription = subOp.get();
        return subscription;
    }
}
