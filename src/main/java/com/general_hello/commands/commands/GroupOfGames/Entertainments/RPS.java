package com.general_hello.commands.commands.GroupOfGames.Entertainments;

import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.commands.PrefixStoring;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class RPS {
	static void rps(GuildMessageReceivedEvent event, String []args){
		final long guildID = event.getGuild().getIdLong();
		String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, DatabaseManager.INSTANCE::getPrefix);

		if (args[0].toLowerCase().contains((prefix + " rps")) && !event.getAuthor().isBot()) {
			Random rand = new Random();
			int c = rand.nextInt(3);
			if(args.length == 1) {
				event.getChannel().sendMessage("Enter a valid response(R,P,S)").queue();
			}else if(args[1].equalsIgnoreCase("R") && c == 0 || args[1].equalsIgnoreCase("P") && c == 1 || args[1].equalsIgnoreCase("S") && c == 2) {
				if(args[1].equalsIgnoreCase("r")) {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("You: Rock").queue();
				}else if(args[1].equalsIgnoreCase("p")) {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("You: Paper").queue();
				}else {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("You: Scissor").queue();
				}
				if(c == 0) {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("Computer: Rock").queue();
				}else if(c == 1) {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("Computer: Paper").queue();
				}else {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("Computer: Scissor").queue();
				}
				event.getChannel().sendTyping().queue();
				event.getChannel().sendMessage("Tie").queue();
			}else if(args[1].equalsIgnoreCase("R") && c == 2 || args[1].equalsIgnoreCase("P") && c == 0 || args[1].equalsIgnoreCase("S") && c == 1) {
				if(args[1].equalsIgnoreCase("r")) {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("You: Rock").queue();
				}else if(args[1].equalsIgnoreCase("p")) {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("You: Paper").queue();
				}else {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("You: Scissor").queue();
				}
				if(c == 0) {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("Computer: Rock").queue();
				}else if(c == 1) {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("Computer: Paper").queue();
				}else {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("Computer: Scissor").queue();
				}
				event.getChannel().sendTyping().queue();
				event.getChannel().sendMessage("Win").queue();
			}else if(args[1].equalsIgnoreCase("R") && c == 1 || args[1].equalsIgnoreCase("P") && c == 2 || args[1].equalsIgnoreCase("S") && c == 0) {
				if(args[1].equalsIgnoreCase("r")) {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("You: Rock").queue();
				}else if(args[1].equalsIgnoreCase("p")) {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("You: Paper").queue();
				}else {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("You: Scissor").queue();
				}
				if(c == 0) {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("Computer: Rock").queue();
				}else if(c == 1) {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("Computer: Paper").queue();
				}else {
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("Computer: Scissor").queue();
				}
				event.getChannel().sendTyping().queue();
				event.getChannel().sendMessage("Lose").queue();
			}else{
				event.getChannel().sendTyping().queue();
				event.getChannel().sendMessage("Enter a valid response(R,P,S)").queue();
			}
		}
	}
}
