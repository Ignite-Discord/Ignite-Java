package com.general_hello.commands.OtherEvents;

import com.general_hello.commands.Config;
import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.commands.GroupOfGames.Games.TriviaCommand;
import com.general_hello.commands.commands.Info.InfoUserCommand;
import com.general_hello.commands.commands.PrefixStoring;
import com.general_hello.commands.commands.RankingSystem.LevelPointManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class OnSelectionMenu extends ListenerAdapter {
    @Override
    public void onSelectionMenu(@NotNull SelectionMenuEvent event) {
        EmbedBuilder embedBuilder;

        SelectionMenu menu = SelectionMenu.create("menu:class")
                .setPlaceholder("Choose the game you want to find help on") // shows the placeholder indicating what this menu is for
                .setRequiredRange(1, 1) // only one can be selected
                .addOption("Uno", "uno")
                .addOption("Blackjack", "bj")
                .addOption("Guess the number", "gn")
                .addOption("Hangman", "hangman")
                .addOption("Trivia", "trivia")
                .addOption("Chess", "chess")
                .build();
        int x = 0;
        String answer = TriviaCommand.storeAnswer.get(event.getUser());

        System.out.println(event.getSelectedOptions().get(0).getValue());
        if (TriviaCommand.storeAnswer.containsKey(event.getUser())) {
            if (event.getSelectedOptions().get(0).getValue().equals(answer)) {
                event.getChannel().sendMessage("Correct answer!!!!\n" +
                        "You got \uD83E\uDE99 1,000 for getting the correct answer").queue();
                LevelPointManager.feed(event.getUser(), 40);
                DatabaseManager.INSTANCE.setCredits(event.getUser().getIdLong(), 1000);
                event.deferEdit().queue();
                event.getMessage().delete().queue();
                TriviaCommand.storeAnswer.remove(event.getUser());
            } else {
                EmbedBuilder e = new EmbedBuilder();
                e.setTitle("Incorrect answer");
                e.setFooter("A correct answer gives you \uD83E\uDE99 1,000");
                e.addField("The correct answer is " + TriviaCommand.storeAnswer.get(event.getUser()), "Better luck next time", false).setColor(Color.RED);
                event.getChannel().sendMessageEmbeds(e.build()).queue();
                event.getMessage().delete().queue();
                TriviaCommand.storeAnswer.remove(event.getUser());
                event.deferEdit().queue();
            }
        }
        while (x < event.getSelectedOptions().size()) {
            switch (event.getSelectedOptions().get(x).getValue()) {
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

                    event.getUser().openPrivateChannel().complete().sendMessageEmbeds(embedBuilder.build()).setActionRow(
                            Button.primary("0000:accept", "Accept").withEmoji(Emoji.fromEmote("verify", 863204252188672000L, true))
                    ).queue();
                    event.getMessage().delete().queue();
                    return;
                case "bj":
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessageEmbeds(helpCrap(1, event).build()).setActionRows(ActionRow.of(menu), ActionRow.of(Button.of(ButtonStyle.DANGER, "0000:backgames", "Back"))).queue();
                    event.deferEdit().queue();
                    break;
                case "gn":
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessageEmbeds(helpCrap(2, event).build()).setActionRows(ActionRow.of(menu), ActionRow.of(Button.of(ButtonStyle.DANGER, "0000:backgames", "Back"))).queue();
                    event.deferReply().queue();
                case "trivia":
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessageEmbeds(helpCrap(4, event).build()).setActionRows(ActionRow.of(menu), ActionRow.of(Button.of(ButtonStyle.DANGER, "0000:backgames", "Back"))).queue();
                    event.deferEdit().queue();
                    break;
                case "chess":
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessageEmbeds(helpCrap(5, event).build()).setActionRows(ActionRow.of(menu), ActionRow.of(Button.of(ButtonStyle.DANGER, "0000:backgames", "Back"))).queue();
                    event.deferEdit().queue();
                    break;
                case "uno":
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessageEmbeds(helpCrap(6, event).build()).setActionRows(ActionRow.of(menu), ActionRow.of(Button.of(ButtonStyle.DANGER, "0000:backgames", "Back"))).queue();
                    event.deferEdit().queue();
                    break;
            }

            x++;
        }
    }

    public EmbedBuilder helpCrap (int number, SelectionMenuEvent ctx) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        switch (number) {
            case 1:
                embedBuilder.setTitle("Blackjack Commands");
                embedBuilder.setColor(Color.YELLOW);
                embedBuilder.addField("1.) Start a blackjack game command","`" + prefix + " blackjack`", false);
                embedBuilder.addField("2.) Hit card command","`" + prefix + " hit`", false);
                embedBuilder.addField("3.) Stand command","`" + prefix + " stand`", false);
                embedBuilder.addField("4.) Double command","`" + prefix + " double`", false);
                embedBuilder.addField("5.) Split card command","`" + prefix + " split`", false);

                embedBuilder.setFooter("\nType " + prefix + " help [command name] to see what they do");
                break;
            case 2:
                embedBuilder.setTitle("Guess the number Commands");
                embedBuilder.setColor(Color.blue);
                embedBuilder.addField("1.) Start the Guess the number game Command", "`" + prefix + " gn start`", false);
                embedBuilder.addField("2.) Guess a number Command", "`" + prefix + " gn [number]`", false);
                embedBuilder.addField("3.) End game Command", "`" + prefix + " gn end`", false);

                embedBuilder.setFooter("\nType " + prefix + " help [command name] to see what they do");
                break;
            case 4:
                embedBuilder.setTitle("Trivia Commands");
                embedBuilder.setColor(Color.CYAN);
                embedBuilder.addField("1.) Start trivia Command", "`" + prefix + " trivia`", false);

                embedBuilder.setFooter("Type " + prefix + " help [command name] to see what they do");
                break;
            case 5:
                embedBuilder.setTitle("Chess Commands");
                embedBuilder.setColor(Color.CYAN);
                embedBuilder.setDescription("Unknown!\n" +
                        "Chess is still being programmed!");
                embedBuilder.setFooter("Type " + prefix + " help [command name] to see what they do");
                break;
            case 6:
                embedBuilder.setTitle("Uno Commands");
                embedBuilder.setColor(Color.blue);
                embedBuilder.addField("1.) Start the UNO game Command", "`" + prefix + " startuno`", false);
                embedBuilder.addField("2.) Play card Command", "`" + prefix + " playcard [card name]`\n" +
                        "Example: `" + prefix + "` playcard red4", false);
                embedBuilder.addField("3.) Draw Card Command", "`" + prefix + " draw`", false);
                embedBuilder.addField("4.) Challenge a +4 card Command", "`" + prefix + " challenge`", false);

                embedBuilder.setFooter("Type " + prefix + " help [command name] to see what they do");
        }
        return embedBuilder;
    }
}
