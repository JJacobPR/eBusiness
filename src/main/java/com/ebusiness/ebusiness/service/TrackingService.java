package com.ebusiness.ebusiness.service;

import com.ebusiness.ebusiness.entity.Tracking;

import java.util.List;
import java.util.Optional;

public interface TrackingService {

    List<Tracking> getAllTrackings();

    Optional<Tracking> getTrackingById(Integer id);

    Tracking createTracking(Tracking tracking);

    Tracking updateTracking(Integer id, Tracking tracking);

    void deleteTracking(Integer id);
}
