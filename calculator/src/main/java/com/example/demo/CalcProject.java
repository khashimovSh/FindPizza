package com.example.demo;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.Serializable;

public class CalcProject extends TelegramLongPollingBot {
    //public static float longditude;
    @Override
    public void onUpdateReceived(Update update) {
//        System.out.println(update.getMessage().getText());
//        System.out.println(update.getMessage().getFrom().getFirstName() +" "+ update.getMessage().getFrom().getLastName());
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setRequestLocation(true);
        String command = update.getMessage().getText();

        SendMessage message = new SendMessage();
//        longditude = update.getMessage().getLocation().getLongitude();
        message.setChatId(update.getMessage().getChatId());
        // updated for making sure if user doesn't enter alphabetic
        if(command!=null){
        try{
            String text = command.strip().toLowerCase();
            if(command.equals("want to eat") ||
                    command.equals("wonna eat")||
                    command.equals("hungry")||
                    command.equals("eat")){
                message.setText("Please send location for finding restaurants");
                execute(message);
            }

        }catch (TelegramApiException e){
            e.printStackTrace();
        }

    }
        if(update.getMessage().getLocation() != null){
            try{
                float longitude = update.getMessage().getLocation().getLongitude();
                float latitude = update.getMessage().getLocation().getLatitude();
                message.setText("McDonalds 200m\n" +
                        "Burger King 100m\n" +
                        "KFC 500m\n"+longitude+" "+latitude);
                execute(message);
            }catch (TelegramApiException e){
                e.printStackTrace();
            }
        }
    }

    private float getMsg(String msg){
        try{
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
            return Float.parseFloat(engine.eval(msg).toString());
        }catch (ScriptException e){
            return 0;
        }
    }

    @Override
    public String getBotUsername() {
        return "Shakhzod";
    }

    @Override
    public String getBotToken() {
        return "1218119673:AAHf2ZCkLy2RpX_qZgzW-x8tmy4pM0zwMjU";
    }


}
