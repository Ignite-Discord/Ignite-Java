package com.general_hello.commands.OtherEvents;

import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.commands.Info.InfoUserCommand;
import com.general_hello.commands.commands.Register.Data;
import com.general_hello.commands.commands.User.UserPhoneUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OnPrivateMessage extends ListenerAdapter {
    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(InfoUserCommand.randomColor());
        if (Data.progress.containsKey(event.getAuthor())) {
            Integer progress = Data.progress.get(event.getAuthor());
            if (progress == 1) {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(event.getMessage().getContentRaw());
                Data.answers.put(event.getAuthor(), arrayList);
                Data.progress.put(event.getAuthor(), 2);

                ArrayList<String> oldAnswers = Data.answers.get(event.getAuthor());

                embedBuilder.setTitle("Account Summary");
                embedBuilder.setDescription("Name: ***" + oldAnswers.get(0) + "***\n" +
                        "Discord name: " + event.getAuthor().getAsMention() + "\n" +
                        "Account creation date: " + DateTimeFormatter.ofPattern("M/d/u h:m:s a").format(event.getAuthor().getTimeCreated()) + "\n" +
                        "Agreed to all rules: ✅").setFooter("Thank you for registering!");
                embedBuilder.setColor(InfoUserCommand.randomColor());
                event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();

                UserPhoneUser user = new UserPhoneUser(oldAnswers.get(0), event.getAuthor());
                Data.userUserPhoneUserHashMap.put(event.getAuthor(), user);
                Data.userPhoneUsers.add(user);

                DatabaseManager.INSTANCE.newInfo(event.getAuthor().getIdLong(), oldAnswers.get(0));
            }
        }
    }
}
