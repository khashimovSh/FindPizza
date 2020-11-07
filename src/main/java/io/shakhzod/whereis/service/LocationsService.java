package io.shakhzod.whereis.service;

import io.shakhzod.whereis.location.LocationPlace;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationsService {

    private static List<LocationPlace> allPlaces = new ArrayList<>();

    public static List<LocationPlace> getAllPlaces(){
        return allPlaces;
    }

    @PostConstruct
    public static void addPlaces(){
        List<LocationPlace> newPlaces = new ArrayList<>();
            LocationPlace burger = new LocationPlace("Burger King",52.1709,20.9373);
            LocationPlace kebab = new LocationPlace("BigSzef",52.2361,21.0086);
            LocationPlace restaurant = new LocationPlace("Manty", 52.2325,20.9604);
            LocationPlace kebab1 = new LocationPlace("Kumpir",52.1595,21.0287);
            LocationPlace yekaterinburg = new LocationPlace("Maximilians Yekaterinburg",56.8265,60.6167);
            LocationPlace yekaterinburg1 = new LocationPlace("Sushi shop",56.830206,60.624389);
            LocationPlace yekaterinburg2 = new LocationPlace("Ratatui",56.837161, 60.633519);
            LocationPlace yekaterinburg3 = new LocationPlace("Avangard",56.870193, 60.652022);

            newPlaces.add(burger);
            newPlaces.add(kebab);
            newPlaces.add(restaurant);
            newPlaces.add(kebab1);
            newPlaces.add(yekaterinburg);
            newPlaces.add(yekaterinburg1);
            newPlaces.add(yekaterinburg2);
            newPlaces.add(yekaterinburg3);
            allPlaces = newPlaces;
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


