package me.yamashitq;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {
    private static JDA jda;
    public static void main(String[] args) {
        try {
            jda = JDABuilder.createDefault("").addEventListeners(new Listener()).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public static JDA getJDA() {
        return jda;
    }
}
