package io.shakhzod.whereis.dao;

import io.shakhzod.whereis.location.LocationRestaurants;

import java.util.List;
import java.util.UUID;

public interface PlacesDao {

    int insertPlace(UUID id, LocationRestaurants locationRestaurants, double latitude, double longitude);
    List<LocationRestaurants> selectAllPlaces();


}
