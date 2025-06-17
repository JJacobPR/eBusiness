package com.ebusiness.ebusiness.service.impl;

import com.ebusiness.ebusiness.entity.Tracking;
import com.ebusiness.ebusiness.repository.TrackingRepository;
import com.ebusiness.ebusiness.service.service.TrackingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackingServiceImpl implements TrackingService {

    private final TrackingRepository trackingRepository;

    TrackingServiceImpl(TrackingRepository trackingRepository) {
        this.trackingRepository = trackingRepository;
    }

    public List<Tracking> getAllTrackings() {
        return trackingRepository.findAll();
    }

    public Optional<Tracking> getTrackingById(Integer id) {
        return trackingRepository.findById(id);
    }

    public Tracking createTracking(Tracking tracking) {
        return trackingRepository.save(tracking);
    }

    public Tracking updateTracking(Integer id, Tracking updatedTracking) {
        return trackingRepository.findById(id)
                .map(existing -> {
                    existing.setOrderID(updatedTracking.getOrderID());
                    existing.setStatus(updatedTracking.getStatus());
                    existing.setTimestamp(updatedTracking.getTimestamp());
                    existing.setLocation(updatedTracking.getLocation());
                    return trackingRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Tracking not found"));
    }

    public void deleteTracking(Integer id) {
        trackingRepository.deleteById(id);
    }
}
