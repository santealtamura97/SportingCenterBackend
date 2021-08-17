package com.sportingCenterWebApp.activityservice.controller;

import com.sportingCenterWebApp.activityservice.model.Activity;
import com.sportingCenterWebApp.activityservice.repo.ActivityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/all")
public class AllActivityController {
    private final ActivityRepository activityRepository;

    public AllActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
    @RequestMapping(value = "activities", method = RequestMethod.GET)
    public @ResponseBody List<Activity> getActivities() {
        return (List<Activity>) activityRepository.findAll();
    }

    @RequestMapping(value = "get_activity/{activityId}", method = RequestMethod.GET)
    public @ResponseBody Activity getActivity(@PathVariable (value = "activityId") String activityId) {
        Optional<Activity> activityOp = activityRepository.findById(Long.valueOf(activityId));
        Activity activity = activityOp.get();
        return activity;
    }
}
