package com.sportingCenterWebApp.calendarservice.controller;

import com.sportingCenterWebApp.calendarservice.model.Event;
import com.sportingCenterWebApp.calendarservice.repo.EventRepository;
import com.sportingCenterWebApp.calendarservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/all")
public class EventAllController {

    @Autowired
    private EventService eventService;

    private EventRepository eventRepository;

    public EventAllController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @RequestMapping(value = "events", method = RequestMethod.GET)
    public @ResponseBody List<Event> getEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    @RequestMapping(value = "date_events/{date}", method = RequestMethod.GET)
    public List<Event> getEventsInDate( @PathVariable("date") String date) {
        return eventService.getEventsInDate(date);
    }
}
