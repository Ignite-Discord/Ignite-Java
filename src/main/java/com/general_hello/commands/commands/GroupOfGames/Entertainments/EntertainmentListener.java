package com.general_hello.commands.commands.GroupOfGames.Entertainments;

import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.commands.PrefixStoring;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EntertainmentListener extends ListenerAdapter{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		long guildID = event.getGuild().getIdLong();
		String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, DatabaseManager.INSTANCE::getPrefix);
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if(args[0].equalsIgnoreCase(prefix + " clap")) {
			Clap.clap(event, args);
			return;
		}
		if(args[0].equalsIgnoreCase(prefix + " colour") || args[0].equalsIgnoreCase(prefix + "color")) {
			Colour.colour(event, args);
			return;
		}
		if(args[0].equalsIgnoreCase(prefix + " eightball")) {
			EightBall.eightball(event, args);
			return;
		}
		if(args[0].equalsIgnoreCase(prefix + " read")) {
			Read.read(event, args);
			return;
		}
		if(args[0].equalsIgnoreCase(prefix + " rps")) {
			RPS.rps(event, args);
			return;
		}
	}
}
