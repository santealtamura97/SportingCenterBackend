package com.sportingCenterWebApp.calendarservice.controller;
import com.sportingCenterWebApp.calendarservice.model.Event;
import com.sportingCenterWebApp.calendarservice.dto.User;
import com.sportingCenterWebApp.calendarservice.repo.EventRepository;
import com.sportingCenterWebApp.calendarservice.service.BookingService;
import com.sportingCenterWebApp.calendarservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class EventController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;


    @PostMapping("/date_events")
    public List<Event> getEventsInDate(@Valid @RequestBody String date) {
        date = date.replaceAll("^\"+|\"+$", "");
        return eventService.getEventsInDate(date);
    }


    @GetMapping("/events")
    public List<Event> getEvents() {
        return eventService.findAll();
    }

    @PostMapping("/event")
    void addEvent(@RequestBody Event event){
        eventService.save(event);
    }

    @PostMapping("/events/delete")
    void deleteEvent(@RequestBody Event event) {
        bookingService.deleteBookingsForEventId(event.getId());
        eventService.delete(event);
    }

    @GetMapping("/getusers/{eventId}")
    public List<User> getUsersForEvent(@PathVariable("eventId") Long eventId) {
        return bookingService.getUsersForEvent(eventId);
    }

    /*@GetMapping("/getpresences/{eventId}")
    public List<User> getPresences(@PathVariable("eventId") Long eventId) {
        return bookingService.getPresences(eventId);
    }*/

    @PostMapping("/add_events")
    void addEvents(@RequestBody List<Event> eventsList){
        for (Event event : eventsList) {
            eventService.save(event);
        }
    }

    @PostMapping("/delete_events")
    void deleteEvents(@RequestBody List<Event> eventsList){
        for (Event event : eventsList) {
            eventService.delete(event);
        }
    }



    /*@GetMapping("/todayevents")
    public List<Event> getTodayEvents() {
        return eventService.getTodayEvents();
    }*/

}

