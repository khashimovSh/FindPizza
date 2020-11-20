package io.shakhzod.whereis.service;

import io.shakhzod.whereis.location.CulturePlace;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CulturePlaceDataService {

    public static List<CulturePlace> culturePlaces = new ArrayList<>();

    private static String CULTURE_PLACE_URL = "https://api.um.warszawa.pl/api/action/wfsstore_get/?id=e26218cb-61ec-4ccb-81cc-fd19a6fee0f8&apikey=cf6ae526-c1c9-4009-a6a4-0be59e4e121f";

    @PostConstruct
    private static void fetchCulturePlaceData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CULTURE_PLACE_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(httpResponse.body());
        StringReader jsonBodyReader = new StringReader(httpResponse.body());
        JSONObject object = new JSONObject(httpResponse.body());
        JSONObject result = object.getJSONObject("result");
        JSONArray array = result.getJSONArray("featureMemberProperties");
        JSONArray coordinates = result.getJSONArray("featureMemberCoordinates");



        for(int i =0; i< array.length(); i++){
            culturePlaces.add(new CulturePlace(array.getJSONObject(i).get("OPIS").toString(),array.getJSONObject(i).get("DZIELNICA").toString(),
                    array.getJSONObject(i).get("ULICA").toString(),array.getJSONObject(i).get("TEL_FAX").toString(),"Hello",coordinates.getJSONObject(i).get("latitude").toString(),
                    coordinates.getJSONObject(i).get("longitude").toString()));
//                    System.out.println(array.getJSONObject(i).get("OBJECTID")+" "+
//                    array.getJSONObject(i).get("ULICA")+" "+
//                    array.getJSONObject(i).get("DZIELNICA")+" "+
//                    array.getJSONObject(i).get("OPIS")+
//                    array.getJSONObject(i).get("TEL_FAX")+
//                    array.getJSONObject(i).get("KOD"));
        }
        culturePlaces.add(new CulturePlace("Zhivoj Teatr","Verkh-Isterskij","Malysheva 58A","207-63-13","web","56.834666","60.608185"));
        culturePlaces.add(new CulturePlace("Ural State Variety Theatre","Dvorec Molodezhi","ul. 8 marta 15","371-15-11","web","56.835603", "60.599884"));
        culturePlaces.add(new CulturePlace("Yekaterinburgskiy Gosudarstvennyy Akademicheskiy Teatr Opery I Baleta","Pros. Lenina","Pros. Lenina 46A","350-80-57","web","56.838825", "60.617192"));

    }

}
