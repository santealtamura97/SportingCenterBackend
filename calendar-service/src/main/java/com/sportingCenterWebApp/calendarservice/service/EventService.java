package com.sportingCenterWebApp.calendarservice.service;

import com.sportingCenterWebApp.calendarservice.dto.Activity;
import com.sportingCenterWebApp.calendarservice.dto.Subscription;
import com.sportingCenterWebApp.calendarservice.dto.User;
import com.sportingCenterWebApp.calendarservice.model.Booking;
import com.sportingCenterWebApp.calendarservice.model.Event;
import com.sportingCenterWebApp.calendarservice.repo.BookingRepository;
import com.sportingCenterWebApp.calendarservice.repo.EventRepository;
import com.sportingCenterWebApp.calendarservice.utils.GeneralUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RestTemplate restTemplate;


    /*public List<Event> getTodayEvents() {
        List<Event> allEvents = (List<Event>) eventRepository.findAll();
        List<Event> todayEvents = new ArrayList<>();
        DateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        now = GeneralUtils.removeTime(now);
        System.out.println(now);
        for (Event event : allEvents) {
            String stringDataEventoNonReversed = StringUtils.substringBefore(event.getInizio(), "T");
            String stringDataEvento = GeneralUtils.aggiustaStringData(stringDataEventoNonReversed);
            Date dataEvento = null;
            try {
                dataEvento = dateFormat.parse(stringDataEvento);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (now.equals(dataEvento)) {
                todayEvents.add(event);
            }
        }
        return todayEvents;
    }*/

    /*public List<Event> getEventsForUser(Long subId, Long userId) {
        //Get Subscription From Subscription Microservice
        Subscription userSubscription = restTemplate.getForObject("http://subscription-service/all/subscriptions/getSubfromid/{subId}",
                Subscription.class, subId);
        Boolean nuoto = userSubscription.getNuoto();
        Boolean fitness = userSubscription.getFitness();

        //Get All Activities
        ResponseEntity<Activity[]> responseEntity = restTemplate.getForEntity("http://activity-service/all/activities", Activity[].class);
        Activity[] activities = responseEntity.getBody();

        //Get Only Activities With Subscription Type
        List<Activity> subActivities = new ArrayList<>();
        for(Activity activity : activities) {
            if (activity.getFitness() == fitness && activity.getNuoto() == nuoto) {
                subActivities.add(activity);
            }
        }

        //Get only events compatible with user subscription and not in the past and for which is not booked yet
        DateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        now = GeneralUtils.removeTime(now);

        List<Event> allEvents = (List<Event>)eventRepository.findAll();
        List<Event> subEvents = new ArrayList<>();
        for(Event event : allEvents) {
            String stringDataEventoNonReversed = StringUtils.substringBefore(event.getInizio(), "T");
            String stringDataEvento = GeneralUtils.aggiustaStringData(stringDataEventoNonReversed);
            Date dataEvento = null;
            try {
                dataEvento = dateFormat.parse(stringDataEvento);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(getActivityById(subActivities, event.getActivityId()) != null && (now.before(dataEvento) || now.equals(dataEvento))){
               if (!checkIsBooked(userId, event.getId()))
                   subEvents.add(event);
            }
        }

        return subEvents;

    }*/

    public List<Event> getEventsForUser(Long subId, Long userId, String date) {

        //Get Subscription From Subscription Microservice
        Subscription userSubscription = restTemplate.getForObject("http://subscription-service/all/subscriptions/getSubfromid/{subId}",
                Subscription.class, subId);
        Boolean nuoto = userSubscription.getNuoto();
        Boolean fitness = userSubscription.getFitness();

        //Get All Activities
        ResponseEntity<Activity[]> responseEntity = restTemplate.getForEntity("http://activity-service/all/activities", Activity[].class);
        Activity[] activities = responseEntity.getBody();

        //Get Only Activities compatible With Subscription Type
        List<Activity> subActivities = new ArrayList<>();
        if (fitness && nuoto) { //fitness e nuoto
            subActivities.addAll(Arrays.asList(activities));
        }else if (fitness) { //fitness
            for(Activity activity : activities) {
                if (activity.getFitness())
                    subActivities.add(activity);
            }
        }else{ //nuoto
            for(Activity activity : activities) {
                if (activity.getNuoto())
                    subActivities.add(activity);
            }
        }
        //get Only events which have activityId in the Ids of idsSubActivities
        //and events with date (obliviously xD)
        List<Event> allEvents = (List<Event>)eventRepository.findAll();
        List<Event> subEvents = new ArrayList<>();
        for (Event event : allEvents) {
            if (contain(subActivities,event.getActivityId())) {
                if (event.getData().equals(date) && !(checkIsBooked(userId, event.getId())) && event.getNumber() > 0) {
                    subEvents.add(event);
                }
            }
        }
        System.out.println("Da avere " + subEvents);
        return subEvents;
    }

    private Boolean contain(List<Activity> subActivities, String id) {
        for (Activity activity : subActivities) {
            if (Long.toString(activity.getId()).equals(id)){
                return true;
            }
        }
        return false;
    }

    public void setEventBooked(String eventId) {
        List<Event> eventList = (List<Event>) eventRepository.findAll();
        for (Event event : eventList) {
            if (Long.toString(event.getId()).equals(eventId)) {
                event.setNumber(event.getNumber() - 1);
                eventRepository.save(event);
            }
        }
    }

    private boolean checkIsBooked(Long userId, Long eventId) {
        for (Booking booking : bookingService.findAll()) {
            if (booking.getUser_id() == userId && booking.getEvent_id().equals(Long.toString(eventId))) {
                return true;
            }
        }
        return false;
    }

    public List<Event> findAllById(List<Long> userEventsIds) {
        List<Event> userEvents = (List<Event>) eventRepository.findAllById(userEventsIds);
        return userEvents;
    }

    public List<Event> findAll() {
        return (List<Event>)eventRepository.findAll();
    }

    public void save(Event event) {
        eventRepository.save(event);
    }

    public void delete(Event event) {
        eventRepository.delete(event);
    }



}
