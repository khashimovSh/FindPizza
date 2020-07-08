package com.example.demo;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CalcProject extends TelegramLongPollingBot {
    private static long chat_id;
    @Override
    public void onUpdateReceived(Update update) {
//        System.out.println(update.getMessage().getText());
//        System.out.println(update.getMessage().getFrom().getFirstName() +" "+ update.getMessage().getFrom().getLastName());

        chat_id =update.getMessage().getChatId();

        String command = update.getMessage().getText();
        SendMessage message = new SendMessage();
        if(command.equals("/mylastname")){
            System.out.println(update.getMessage().getFrom().getLastName());
            message.setText(update.getMessage().getFrom().getLastName());
        }
        else if(command.equals("/myfullname")){
            System.out.println(update.getMessage().getFrom().getFirstName()+" "+update.getMessage().getFrom().getLastName());
            message.setText(update.getMessage().getFrom().getFirstName()+" "+update.getMessage().getFrom().getLastName());
        }

        message.setChatId(update.getMessage().getChatId());

        try{
            message.setText("You entered: " + command);
            execute(message);

            message.setText("The result is: " + (getMsg(command)));
            execute(message);
        }catch (TelegramApiException e){
            e.printStackTrace();
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
