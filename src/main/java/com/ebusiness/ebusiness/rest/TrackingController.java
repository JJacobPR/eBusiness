package com.ebusiness.ebusiness.rest;

import com.ebusiness.ebusiness.entity.Tracking;
import com.ebusiness.ebusiness.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trackings")
public class TrackingController {

    @Autowired
    private TrackingService trackingService;

    @GetMapping
    public List<Tracking> getAllTrackings() {
        return trackingService.getAllTrackings();
    }

    @GetMapping("/{id}")
    public Tracking getTrackingById(@PathVariable Integer id) {
        return trackingService.getTrackingById(id)
                .orElseThrow(() -> new RuntimeException("Tracking not found"));
    }

    @PostMapping
    public Tracking createTracking(@RequestBody Tracking tracking) {
        return trackingService.createTracking(tracking);
    }

    @PutMapping("/{id}")
    public Tracking updateTracking(@PathVariable Integer id, @RequestBody Tracking tracking) {
        return trackingService.updateTracking(id, tracking);
    }

    @DeleteMapping("/{id}")
    public void deleteTracking(@PathVariable Integer id) {
        trackingService.deleteTracking(id);
    }
}
