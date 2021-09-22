package com.general_hello.commands.OtherEvents;

import com.general_hello.commands.commands.GetData;
import com.general_hello.commands.commands.Info.InfoUserCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class OnSelectionMenu extends ListenerAdapter {
    @Override
    public void onSelectionMenu(@NotNull SelectionMenuEvent event) {
        switch (event.getSelectedOptions().get(0).getValue()) {
            case "enableXP":
                String isEnabled = "Enabled";
                boolean isEnabledBoolean = true;
                boolean isDisabledBoolean = false;
                if (GetData.blackListedGuild.contains(event.getGuild())) {
                    isEnabled = "Disabled";
                    isEnabledBoolean = false;
                    isDisabledBoolean = true;
                }

                EmbedBuilder embedBuilder = new EmbedBuilder().setTitle(event.getGuild().getName() + "'s settings for XP System").setFooter("Your settings ↔ XP System Setting Page").setColor(Color.ORANGE);
                embedBuilder.setDescription("XP system - Grants users experience points (XP) and levels based on their activity in a server. Its main purpose is to reward member activity in the community.\n\n" +
                        "Current setting: **" + isEnabled + "**");

                event.replyEmbeds(embedBuilder.build()).setEphemeral(true)
                        .addActionRow(
                                Button.primary(event.getUser().getId() + ":enableXPSystem", "Enable").withDisabled(isEnabledBoolean),
                                Button.primary(event.getUser().getId() + ":disableXPSystem", "Disable").withDisabled(isDisabledBoolean)
                        ).queue();
                return;
            case "reject":
                event.getUser().openPrivateChannel().complete().sendMessage("Sorry, you are too young to use this bot! (You shouldn't be on Discord!)").queue();
                event.getMessage().delete().queue();
                return;
            case "noice":
            case "oh":
            case "old":
                embedBuilder = new EmbedBuilder().setTitle("Rules").setColor(InfoUserCommand.randomColor());
                String arrow = "<a:arrow_1:862525611465113640>";
                String message = arrow + " THIS IS A CHRISTIAN COMMUNITY SERVER. That means, we value the things Christ teaches us! " + com.general_hello.commands.commands.Emoji.Emoji.USER + " Let us try our best to exemplify Christlikness in all that we do here! " + com.general_hello.commands.commands.Emoji.Emoji.CHECK + "\n" +
                        "\n" +
                        arrow + " THIS GROUP IS FOR HIGH SCHOOL STUDENTS ONLY. High schoolers and Ignite friends (like your Ahyas and Achis) are the only ones allowed to join this server. This is to ensure your safety and security! " + com.general_hello.commands.commands.Emoji.Emoji.MOD + "\n" +
                        "\n" +
                        arrow + " INVITE HIGH SCHOOLERS TO JOIN OUR PROGRAMS! COIL was made to provide an avenue for high school students to connect in a safe, Christian community! Let's help our community grow by inviting your friends to hangout with us! \n" +
                        "\n" +
                        arrow + " BE COURTEOUS IN YOUR SPEECH. Out of the overflow of the heart, the mouth speaks! Let's avoid saying words that hurt others and cause people to stumble! Instead, let us encourage and uplift one another!\n" +
                        "\n" +
                        arrow + " SHOW LOVE TO EVERYONE. In COIL, we do not tolerate bullying of any sort! Kindly make an effort to love one another, even in situations wherein our uniqueness makes it harder for us to do so! Let's make COIL a safe space for everyone to hang!";

                embedBuilder.setDescription(message);
                embedBuilder.setFooter("Press the Accept button if you accept the rules stated above!");

                String id = event.getUser().openPrivateChannel().complete().sendMessageEmbeds(embedBuilder.build()).setActionRow(
                        Button.primary("0000:accept", "Accept").withEmoji(Emoji.fromEmote("verify", Long.parseLong("803768813110951947"), true))
                ).complete().getPrivateChannel().getId();
                event.getMessage().delete().queue();
                return;
            case "n/a":
                return;
            default:
                event.deferReply().queue();
        }

        event.deferEdit().queue();
    }
}
