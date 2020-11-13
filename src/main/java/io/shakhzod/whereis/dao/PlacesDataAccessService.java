package io.shakhzod.whereis.dao;

import io.shakhzod.whereis.location.LocationPlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("postgres")
public class PlacesDataAccessService implements PlacesDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PlacesDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    

    @Override
    public int insertPlace(UUID id, LocationPlace locationPlace, double latitude, double longitude) {
        return 0;
    }

    @Override
    public List<LocationPlace> selectAllPlaces() {
        final String sql = "SELECT name, latitude, longitude,address,web,hours FROM place";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            String name = resultSet.getString("name");
            double latitude = resultSet.getDouble("latitude");
            double longitude = resultSet.getDouble("longitude");
            String address = resultSet.getString("address");
            String web = resultSet.getString("web");
            String hours = resultSet.getString("hours");
            return new LocationPlace(name, latitude, longitude,address,web,hours);
        });
    }

    public List<LocationPlace> selectNearby(int radius){
        final String sql ="SELECT name FROM place WHERE"+radius+" Math.acos(Math.sin(radLat) * Math.sin(pr.getRadLat())"
                +" Math.cos(radLat) * Math.cos(pr.getRadLat()) * Math.cos(radLon - pr.getRadLon())) * 6371";
        return null;
    }

}
