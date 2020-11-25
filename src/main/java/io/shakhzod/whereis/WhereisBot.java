package io.shakhzod.whereis;

import io.shakhzod.whereis.dao.PlacesDataAccessService;
import io.shakhzod.whereis.location.CulturePlace;
import io.shakhzod.whereis.location.LocationRestaurants;
import io.shakhzod.whereis.location.LocationTransformer;
import io.shakhzod.whereis.service.CulturePlaceDataService;
import io.shakhzod.whereis.service.Emoji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


@Component
public class WhereisBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    private final String Digits = "(\\p{Digit}+)";
    private static double distance;
    @Autowired
    private PlacesDataAccessService placesDataAccessService;

    private static int restaurant = 0;
    private static int theatre = 0;

    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    @Override
    public void onUpdateReceived(Update update) {

        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        String textFromUser = update.getMessage().getText();
        Location location = update.getMessage().getLocation();

        message.setReplyMarkup(replyKeyboardMarkup);

        List<LocationRestaurants> locationRestaurants = placesDataAccessService.selectAllPlaces();

        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThirdRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);


        if(textFromUser != null){
            try{
              textFromUser = textFromUser.toLowerCase();
              if(textFromUser.equals("hello")||textFromUser.equals("hi")||textFromUser.equals("start")||
               textFromUser.equals("/start")){
               message.setText("Hello"+Emoji.WINKING_FACE+" "+update.getMessage().getFrom().getFirstName()+" "+update.getMessage().getFrom().getLastName()+" \n"
               +"I am your assistant to find proper places to eat "+Emoji.KITCHEN_GRAPE+Emoji.KITCHEN_BURGER+Emoji.KITCHEN_FRENCH_FRIES+"\n"
               +"Please tell me, are you hungry?"+Emoji.KITCHEN_FORK_AND_KNIFE);

               keyboard.clear();
               keyboardFirstRow.clear();
               keyboardSecondRow.clear();
               keyboardFirstRow.add("Yes"+Emoji.KITCHEN_WANT_TO_EAT_FACE);
               keyboardSecondRow.add("No!");
               keyboardThirdRow.add("Want to go to theatre"+Emoji.THEATRE_FACE);
               keyboard.add(keyboardFirstRow);
               keyboard.add(keyboardSecondRow);
               keyboard.add(keyboardThirdRow);
               replyKeyboardMarkup.setKeyboard(keyboard);

               execute(message);
               }
               if(textFromUser.equals("yes"+Emoji.KITCHEN_WANT_TO_EAT_FACE)||textFromUser.equals("maybe anyway i will go, why not"+Emoji.KITCHEN_FACE_VERY_SMART)
                  ||textFromUser.equals("now i'm hungry"+Emoji.KITCHEN_FACE_SMART)||textFromUser.equals("want to eat something"+Emoji.KITCHEN_WANT_TO_EAT_FACE)||
                    textFromUser.equals("enter bigger number")){
                   restaurant++;
                   keyboard.clear();
                   keyboardFirstRow.clear();
                   keyboardSecondRow.clear();
                   keyboardFirstRow.add("5");
                   keyboardFirstRow.add("10");
                   keyboardSecondRow.add("Or just text me, in number"+Emoji.SMILING_FACE_WITH_OPEN_MOUTH);
                   keyboard.add(keyboardFirstRow);
                   keyboard.add(keyboardSecondRow);
                   replyKeyboardMarkup.setKeyboard(keyboard);
                   message.setText("Tell me, what distance is okay"+"\n"+"for you, please enter in km\n" +
                           Emoji.KITCHEN_SIGN+"Enter just number "+Emoji.KITCHEN_NUMBER+" no other character is allowed!");
                   execute(message);
               }
               if(textFromUser.equals("no!")){
                   keyboard.clear();
                   keyboardFirstRow.clear();
                   keyboardSecondRow.clear();
                   keyboardFirstRow.add("Now I'm hungry"+Emoji.KITCHEN_FACE_SMART);
                   keyboardSecondRow.add("Maybe anyway I will go, why not"+Emoji.KITCHEN_FACE_VERY_SMART);
                   keyboard.add(keyboardSecondRow);
                   keyboard.add(keyboardFirstRow);
                   replyKeyboardMarkup.setKeyboard(keyboard);
                   message.setText("No problem) let me know when you want"+Emoji.KITCHEN_FACE_SMILE);
                   execute(message);
               }
               if(textFromUser.matches(Digits)){
                   distance = Double.parseDouble(textFromUser);
                   keyboard.clear();
                   keyboardFirstRow.clear();
                   keyboardSecondRow.clear();
                   keyboardFirstRow.add("Location"+Emoji.KITCHEN_LOCATION+" is required to give you appropriate choices"+Emoji.KITCHEN_FACE_SMILE);
                   keyboard.add(keyboardFirstRow);
                   replyKeyboardMarkup.setKeyboard(keyboard);
                   message.setText("Almost Done"+Emoji.KITCHEN_FACE_SMILE+"\nPlease send me your location"+Emoji.KITCHEN_LOCATION);
                   execute(message);
               }
               if(textFromUser.equals("location"+Emoji.KITCHEN_LOCATION+" is required to give you appropriate choices"+Emoji.KITCHEN_FACE_SMILE)){
                   message.setText("I'm waiting location from you"+Emoji.KITCHEN_FACE_ROTATED);
                   execute(message);
               }
               if(textFromUser.equals("or just text me, in number"+Emoji.SMILING_FACE_WITH_OPEN_MOUTH)){
                   message.setText("I'm waiting from you number, only number"+Emoji.KITCHEN_FACE_ROTATED);
                   execute(message);
               }
               if(textFromUser.equals("want to go to theatre"+Emoji.THEATRE_FACE)||textFromUser.equals("enter larger number")){
                   theatre--;
                   keyboard.clear();
                   keyboardFirstRow.clear();
                   keyboardSecondRow.clear();
                   keyboardThirdRow.clear();
                   keyboardFirstRow.add("5");
                   keyboardFirstRow.add("10");
                   keyboardSecondRow.add("Or just text me, in number"+Emoji.SMILING_FACE_WITH_OPEN_MOUTH);
                   keyboard.add(keyboardFirstRow);
                   keyboard.add(keyboardSecondRow);
                   replyKeyboardMarkup.setKeyboard(keyboard);
                   message.setText("Tell me, what distance is okay"+"\n"+"for you, please enter in km\n" +
                           Emoji.KITCHEN_SIGN+"Enter just number "+Emoji.KITCHEN_NUMBER+" no other character is allowed!");
                   execute(message);

                }
               if(textFromUser.equals("go to main menu"+Emoji.THEATRE_MENU)||textFromUser.equals("go to main menu")){
                   keyboard.clear();
                   keyboardFirstRow.add("Want to eat something"+Emoji.KITCHEN_WANT_TO_EAT_FACE);
                   keyboardSecondRow.add("Want to go to theatre"+Emoji.THEATRE_FACE);
                   keyboard.add(keyboardFirstRow);
                   keyboard.add(keyboardSecondRow);
                   replyKeyboardMarkup.setKeyboard(keyboard);
                   message.setText("You are in main menu"+Emoji.KITCHEN_FACE_SMILE+"\n"+
                           "please choose options"+Emoji.THEATRE_PAGER);
                   execute(message);
               }





            }catch(TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if(location != null){
            try {

                LocationTransformer userLocation = new LocationTransformer(location.getLatitude(),location.getLongitude());
                System.out.println(theatre);
                System.out.println(restaurant);
                if(restaurant>0){
                    int restCount = 0;
                    keyboard.clear();
                    keyboardFirstRow.add("Go to main menu"+Emoji.THEATRE_MENU);
                    keyboard.add(keyboardFirstRow);
                    replyKeyboardMarkup.setKeyboard(keyboard);
                    restaurant = 0;
                for(LocationRestaurants i : locationRestaurants){
                    LocationTransformer placeLoc = new LocationTransformer(i.getLatitude(),i.getLongitude());
                    double dist = Math.acos(Math.sin(userLocation.getRadLat()) * Math.sin(placeLoc.getRadLat()) +
                            Math.cos(userLocation.getRadLat()) * Math.cos(placeLoc.getRadLat()) * Math.cos(userLocation.getRadLon() - placeLoc.getRadLon())) * 6371;
                    double roundOff = Math.round(dist * 100.0) / 100.0;
                    if(dist <= distance) {
                        restCount++;
                        message.setText(Emoji.KITCHEN_SALAD + i.getName() + "\n"
                                + Emoji.KITCHEN_WALKING_MAN + roundOff + " km away from you!\n"+
                                Emoji.KITCHEN_CITY+i.getAddress()+"\n"+
                                Emoji.KITCHEN_LAPTOP+i.getWeb()+"\n"+
                                Emoji.KITCHEN_TIME+i.getHours());
                        execute(message);
                    }
                }
                    if(restCount==0){
                    keyboard.clear();
                    keyboardFirstRow.clear();
                    keyboardSecondRow.clear();
                    keyboardFirstRow.add("Enter bigger number");
                    keyboardSecondRow.add("Go to main menu"+Emoji.THEATRE_MENU);
                    keyboard.add(keyboardFirstRow);
                    keyboard.add(keyboardSecondRow);
                    replyKeyboardMarkup.setKeyboard(keyboard);
                    System.out.println(theatre);
                    message.setText("Sorry but no restaurant"+"\n"+"in radius 5km"+Emoji.CRYING_FACE+"\n"
                            + "Please choose option"+"\n"+"\"Enter bigger number\""+"\n"
                            + "and select or enter number"+Emoji.KITCHEN_FACE_SMILE);
                    execute(message);
                }else{
                message.setText("Bon appétit"+Emoji.KITCHEN_COFFEE);
                execute(message);
                }
                }
                if(theatre<0){
                    theatre = 0;
                    int k = 0;
                    keyboard.clear();
                    keyboardFirstRow.add("Go to main menu"+Emoji.THEATRE_MENU);
                    keyboard.add(keyboardFirstRow);
                    replyKeyboardMarkup.setKeyboard(keyboard);
                    for(CulturePlace i : CulturePlaceDataService.culturePlaces){
                        LocationTransformer placeLoc = new LocationTransformer(Double.parseDouble(i.getLatitude()),Double.parseDouble(i.getLongitude()));
                        double dist = Math.acos(Math.sin(userLocation.getRadLat()) * Math.sin(placeLoc.getRadLat()) +
                                Math.cos(userLocation.getRadLat()) * Math.cos(placeLoc.getRadLat()) * Math.cos(userLocation.getRadLon() - placeLoc.getRadLon())) * 6371;
                        double roundOff = Math.round(dist * 100.0) / 100.0;

                        if(dist <= distance) {
                            k++;
                            message.setText(Emoji.THEATRE_FACE+i.getName() + "\n" +
                                    Emoji.THEATRE_DISTRICT+i.getDistrict() + "\n" +
                                    Emoji.KITCHEN_WALKING_MAN + roundOff + " km away from you!\n" +
                                    Emoji.KITCHEN_CITY+i.getStreet() + "\n" +
                                    Emoji.THEATRE_PHONE+i.getTelephone());
                            execute(message);
                        }
                 }
                    if(k==0){
                        keyboard.clear();
                        keyboardFirstRow.clear();
                        keyboardSecondRow.clear();
                        keyboardFirstRow.add("Enter larger number");
                        keyboardSecondRow.add("Go to main menu"+Emoji.THEATRE_MENU);
                        keyboard.add(keyboardFirstRow);
                        keyboard.add(keyboardSecondRow);
                        replyKeyboardMarkup.setKeyboard(keyboard);
                        System.out.println(theatre);
                        message.setText("Sorry but no theatre in radius 5km"+Emoji.CRYING_FACE+"\n"
                                + "Please choose option"+"\n"+"\"Enter larger number\""+"\n"
                                + "and select or enter number"+Emoji.KITCHEN_FACE_SMILE);
                        execute(message);
                    }
                }

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
