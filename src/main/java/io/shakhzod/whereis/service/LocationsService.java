package io.shakhzod.whereis.service;

import io.shakhzod.whereis.dao.PlacesDataAccessService;
import io.shakhzod.whereis.location.LocationRestaurants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationsService {
    private static PlacesDataAccessService placesDataAccessService;
    private static List<LocationRestaurants> allPlaces = new ArrayList<>();

    public static List<LocationRestaurants> getAllPlaces(){
        return allPlaces;
    }

    

    }
//    public boolean addPlaces(String name, float longitude, float latitude){
//        if(name == null || longitude < Math.toRadians(-180d) || longitude > Math.toRadians(180d)
//        || latitude < Math.toRadians(-90d) || latitude > Math.toRadians(-90d)){
//            System.out.println("You forgot add name of place, or you entered illegal longitude" +
//                    "or latitude, please try again!");
//            return false;
//        }else{
//            Location location = new Location();
//            location.setName(name);
//            location.setLongitude(longitude);
//            location.setLatitude(latitude);
//            return true;
//        }
//    }


