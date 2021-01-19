package com.sportingCenterWebApp.calendarservice.repo;

import com.sportingCenterWebApp.calendarservice.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
}
