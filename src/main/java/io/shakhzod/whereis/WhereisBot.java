package io.shakhzod.whereis;

import io.shakhzod.whereis.location.LocationPlace;
import io.shakhzod.whereis.location.LocationFilter;
import io.shakhzod.whereis.service.LocationsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


@Component
public class WhereisBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    private final String Digits = "(\\p{Digit}+)";
    private static double distance;

    @Override
    public void onUpdateReceived(Update update) {

        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        String textFromUser = update.getMessage().getText();
        Location location = update.getMessage().getLocation();


        if(textFromUser != null){
            try{
               textFromUser = textFromUser.toLowerCase();
               if(textFromUser.equals("hello")||textFromUser.equals("hi")||textFromUser.equals("start")||
               textFromUser.equals("/start")){
               message.setText("Hello! "+update.getMessage().getFrom().getUserName()+" \n"
               +"I am your assistant to find proper places to eat :) \n"
               +"Please tell me, are you hungry?)");
               execute(message);
               }
               if(textFromUser.equals("yes")||textFromUser.equals("sure")||textFromUser.equals("yes!")||textFromUser.equals("sure!")
               ||textFromUser.equals("yes, of course")||textFromUser.equals("of course")||textFromUser.equals("yes of course!")
               ||textFromUser.equals("of course!")||textFromUser.equals("i don't know")){

                   message.setText("Tell me, what distance is okay for you, please enter in km\n" +
                           "P.S. enter just number, no other character is allowed!");
                   execute(message);

               }
               if(textFromUser.equals("no")||textFromUser.equals("not now")||textFromUser.equals("no, thank you")){
                   message.setText("No problem) let me know when you want :)");
                   execute(message);
               }
               if(textFromUser.matches(Digits)){
                   distance = Double.parseDouble(textFromUser);
                   message.setText("Almost Done!\nPlease send me your location!");
                   execute(message);
               }




            }catch(TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if(location != null){
            try {

                LocationFilter userLocation = new LocationFilter(location.getLatitude(),location.getLongitude());
                List<String> nearLocationPlace = userLocation.placesNear(distance);
                for(String i : nearLocationPlace){
                    message.setText(i+" km from you");
                    execute(message);
                }
                message.setText("Bon appétit!");
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
