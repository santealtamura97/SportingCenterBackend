package com.sportingCenterWebApp.calendarservice.controller;

import com.sportingCenterWebApp.calendarservice.model.Booking;
import com.sportingCenterWebApp.calendarservice.model.Event;
import com.sportingCenterWebApp.calendarservice.repo.BookingRepository;
import com.sportingCenterWebApp.calendarservice.repo.EventRepository;
import com.sportingCenterWebApp.calendarservice.service.BookingService;
import com.sportingCenterWebApp.calendarservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class EventUserController {

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;


    @RequestMapping(value = "user_date_events/{subId}/{userId}/{date}", method = RequestMethod.PUT)
    public List<Event> getEventsForUser(@PathVariable("subId") Long subId, @PathVariable("userId") Long userId, @PathVariable("date") String date) {
        return eventService.getEventsForUser(subId, userId, date);
    }

    @RequestMapping(value = "user_events", method = RequestMethod.GET)
    public List<Event> getEvents() {
        return eventService.findAll();
    }

    @RequestMapping(value = "delete_booking/{userId}/{eventId}", method = RequestMethod.PUT)
    public void deleteBooking(@PathVariable("userId") Long userId, @PathVariable("eventId") Long eventId){
        bookingService.deleteBooking(userId,eventId);
    }

    @PutMapping("book_event/{idUser}/{eventId}")
    public void bookEvent(@PathVariable("idUser") Long userId, @PathVariable("eventId") String eventId) {
        eventService.setEventBooked(eventId);
        bookingService.bookEvent(userId,eventId);
    }

    @RequestMapping(value = "user_bookings/{userId}", method = RequestMethod.GET)
    public List<Event> getBookingForUser(@PathVariable("userId") Long userId) {
        return eventService.findAllById(bookingService.getBookingsIdForUserId(userId));
    }

    /*@GetMapping("/todayevents")
    public List<Event> getTodayEvents() {
       return eventService.getTodayEvents();
    }*/
}
