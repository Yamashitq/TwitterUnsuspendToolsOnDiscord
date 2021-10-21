package me.yamashitq;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.io.*;
import java.util.Random;

public class Listener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();
        User user = event.getAuthor();
        String content = message.getContentDisplay();

        if (event.getChannel().getType() == ChannelType.PRIVATE) {
            return;
        }
        if (!user.isBot()) {
            if (content.contains("/unsus")) {
                String[] args = content.split(" ");
                if (args.length == 2) {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setColor(Color.BLUE)
                            .setTitle("申請します！");
                    Message m = event.getTextChannel().sendMessage(embedBuilder.build()).complete();
                    int leftLimit = 97; // letter 'a'
                    int rightLimit = 122; // letter 'z'
                    int targetStringLength = 10;
                    Random random = new Random();
                    StringBuilder buffer = new StringBuilder(targetStringLength);
                    for (int i = 0; i < targetStringLength; i++) {
                        int randomLimitedInt = leftLimit + (int)
                                (random.nextFloat() * (rightLimit - leftLimit + 1));
                        buffer.append((char) randomLimitedInt);
                    }
                    String generatedString = buffer.toString();
                    File f = new File("./unsus.json");
                    if (!f.exists()) {
                        try {
                            f.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        FileWriter fw = new FileWriter(f);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write("{\"id\": \"" + args[1] + "\", \"email\": \"" + "Email" + "\", \"response\": \"" + generatedString + "\"}");
                        bw.close();
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        ProcessBuilder processBuilder = new ProcessBuilder("./unsus.bat");
                        processBuilder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    new Thread(() -> {
                        for (int a = 0;a<1800;a++) {
                            try {
                                File file = new File("./" + generatedString + ".json");
                                if (file.exists()) {
                                    embedBuilder.setColor(Color.GREEN)
                                            .setTitle("申請成功しました！")
                                            .appendDescription("ID: " + args[1]);
                                    m.editMessage(embedBuilder.build()).queue();
                                    break;
                                }
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        File file = new File("./" + generatedString + ".json");
                        if (file.exists()) {
                            file.delete();
                        }
                        else if (!file.exists()) {
                            embedBuilder.setColor(Color.RED)
                                    .setTitle("申請失敗しました")
                                    .appendDescription("ID: " + args[1]);
                            m.editMessage(embedBuilder.build()).queue();
                        }
                    }).start();
                }
                if (args.length == 3) {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setColor(Color.BLUE)
                            .setTitle("申請します！");
                    Message m = event.getTextChannel().sendMessage(embedBuilder.build()).complete();
                    int leftLimit = 97; // letter 'a'
                    int rightLimit = 122; // letter 'z'
                    int targetStringLength = 10;
                    Random random = new Random();
                    StringBuilder buffer = new StringBuilder(targetStringLength);
                    for (int i = 0; i < targetStringLength; i++) {
                        int randomLimitedInt = leftLimit + (int)
                                (random.nextFloat() * (rightLimit - leftLimit + 1));
                        buffer.append((char) randomLimitedInt);
                    }
                    String generatedString = buffer.toString();
                    File f = new File("./unsus.json");
                    if (!f.exists()) {
                        try {
                            f.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        FileWriter fw = new FileWriter(f);
                        BufferedWriter bw = new BufferedWriter(fw);
                        if (event.getGuild().getId().equals("871688619310608455")) {
                            bw.write("{\"id\": \"" + args[1] + "\", \"email\": \"" + args[2] + "\", \"response\": \"" + generatedString + "\"}");
                        }
                        else {
                            bw.write("{\"id\": \"" + args[1] + "\", \"email\": \"" + args[2] + "\", \"response\": \"" + generatedString + "\"}");
                        }
                        bw.close();
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        ProcessBuilder processBuilder = new ProcessBuilder("./unsus.bat");
                        processBuilder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    new Thread(() -> {
                        for (int a = 0;a<1800;a++) {
                            try {
                                File file = new File("./" + generatedString + ".json");
                                if (file.exists()) {
                                    embedBuilder.setColor(Color.GREEN)
                                            .setTitle("申請成功しました！")
                                            .appendDescription("ID: " + args[1]);
                                    m.editMessage(embedBuilder.build()).queue();
                                    break;
                                }
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        File file = new File("./" + generatedString + ".json");
                        if (file.exists()) {
                            file.delete();
                        }
                        else if (!file.exists()) {
                            embedBuilder.setColor(Color.RED)
                                    .setTitle("申請失敗しました")
                                    .appendDescription("ID: " + args[1]);
                            m.editMessage(embedBuilder.build()).queue();
                        }
                    }).start();
                }
            }
        }
    }
}
