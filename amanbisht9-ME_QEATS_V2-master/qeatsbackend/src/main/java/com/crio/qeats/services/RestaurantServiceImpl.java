
/*
 *
 *  * Copyright (c) Crio.Do 2019. All rights reserved
 *
 */

package com.crio.qeats.services;

import com.crio.qeats.dto.Restaurant;
import com.crio.qeats.exchanges.GetRestaurantsRequest;
import com.crio.qeats.exchanges.GetRestaurantsResponse;
import com.crio.qeats.repositoryservices.RestaurantRepositoryService;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RestaurantServiceImpl implements RestaurantService {

  private final Double peakHoursServingRadiusInKms = 3.0;
  private final Double normalHoursServingRadiusInKms = 5.0;
  
  @Autowired(required = true)
  private RestaurantRepositoryService restaurantRepositoryService;


  // TODO: CRIO_TASK_MODULE_RESTAURANTSAPI - Implement findAllRestaurantsCloseby.
  // Check RestaurantService.java file for the interface contract.
  @Override
  public GetRestaurantsResponse findAllRestaurantsCloseBy(
      GetRestaurantsRequest getRestaurantsRequest, LocalTime currentTime) {

        List<Restaurant> restaurants = new ArrayList<Restaurant>();

        // LocalTime peakMorningStart = LocalTime.of(8, 0);
        // LocalTime peakMorningEnd = LocalTime.of(10, 0);
        // LocalTime peakAfternoonStart = LocalTime.of(13, 0);
        // LocalTime peakAfternoonEnd = LocalTime.of(14, 0);
        // LocalTime peakEveningStart = LocalTime.of(19, 0);
        // LocalTime peakEveningEnd = LocalTime.of(21, 0);

        double servingRadius = 0.0;
        int h = currentTime.getHour();
        int m = currentTime.getMinute();
        if ((h >= 8 && h <= 9) || (h == 10 && m == 0) || (h >= 13 && h <= 14) || (h >= 19 && h <= 20) || (h == 21 && m == 0))
    {
          servingRadius = peakHoursServingRadiusInKms;
        } else {
          servingRadius = normalHoursServingRadiusInKms;
        }

        restaurants = restaurantRepositoryService.findAllRestaurantsCloseBy(
            getRestaurantsRequest.getLatitude(), 
            getRestaurantsRequest.getLongitude(),
            currentTime,
            servingRadius
        );

        GetRestaurantsResponse getRestaurantsResponse = new GetRestaurantsResponse(restaurants);

        return getRestaurantsResponse;
  }


}

