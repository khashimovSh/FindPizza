package io.shakhzod.whereis;

import io.shakhzod.whereis.dao.PlacesDataAccessService;
import io.shakhzod.whereis.location.LocationPlace;
import io.shakhzod.whereis.location.LocationFilter;
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

    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    @Override
    public void onUpdateReceived(Update update) {

        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        String textFromUser = update.getMessage().getText();
        Location location = update.getMessage().getLocation();

        message.setReplyMarkup(replyKeyboardMarkup);

        List<LocationPlace> locationPlaces = placesDataAccessService.selectAllPlaces();

        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

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
               keyboard.add(keyboardFirstRow);
               keyboard.add(keyboardSecondRow);
               replyKeyboardMarkup.setKeyboard(keyboard);

               execute(message);
               }
               if(textFromUser.equals("yes"+Emoji.KITCHEN_WANT_TO_EAT_FACE)||textFromUser.equals("maybe anyway i will go, why not"+Emoji.KITCHEN_FACE_VERY_SMART)
                  ||textFromUser.equals("now i'm hungry"+Emoji.KITCHEN_FACE_SMART)){
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





            }catch(TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if(location != null){
            try {

                LocationFilter userLocation = new LocationFilter(location.getLatitude(),location.getLongitude());


                for(LocationPlace i : locationPlaces){
                    LocationFilter placeLoc = new LocationFilter(i.getLatitude(),i.getLongitude());
                    double dist = Math.acos(Math.sin(userLocation.getRadLat()) * Math.sin(placeLoc.getRadLat()) +
                            Math.cos(userLocation.getRadLat()) * Math.cos(placeLoc.getRadLat()) * Math.cos(userLocation.getRadLon() - placeLoc.getRadLon())) * 6371;
                    double roundOff = Math.round(dist * 100.0) / 100.0;
                    if(dist <= distance) {
                        message.setText(Emoji.KITCHEN_SALAD + i.getName() + "\n"
                                + Emoji.KITCHEN_WALKING_MAN + roundOff + " km away from you!\n"+
                                Emoji.KITCHEN_CITY+i.getAddress()+"\n"+
                                Emoji.KITCHEN_LAPTOP+i.getWeb()+"\n"+
                                Emoji.KITCHEN_TIME+i.getHours());
                        execute(message);
                    }
                }
                message.setText("Bon appétit"+Emoji.KITCHEN_COFFEE);
                execute(message);

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
