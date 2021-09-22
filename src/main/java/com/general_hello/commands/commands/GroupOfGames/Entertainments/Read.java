package com.general_hello.commands.commands.GroupOfGames.Entertainments;

import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.commands.PrefixStoring;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Read {
	static void read(GuildMessageReceivedEvent event, String []args){
		final long guildID = event.getGuild().getIdLong();
		String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, DatabaseManager.INSTANCE::getPrefix);

		if (args[0].equalsIgnoreCase(prefix + " read")) {
			Message message = event.getMessage();
			if(message.getAttachments().size() == 0){
				event.getChannel().sendMessage("Attach a file that you want to read!").queue();
			}else{
				InputStream stream;
				try {
					stream = message.getAttachments().get(0).retrieveInputStream().get();
					ArrayList<Character> arr = new ArrayList<>();
					int b;
					while((b = stream.read()) != -1)
						arr.add((char) b);
					char[] unboxed = new char[arr.size()];
					for(int i = 0; i < unboxed.length; i++)
						unboxed[i] = arr.get(i);
					String s = String.valueOf(unboxed);
					EmbedBuilder text = new EmbedBuilder()
							.setTitle("Received a file! \nBytes read:")
							.setColor(0x7289DA)
							.setDescription(s);
					event.getChannel().sendMessageEmbeds(text.build()).queue();
				} catch (InterruptedException | ExecutionException | IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					event.getChannel().sendMessage("*Error, Looks like your document has more than 2048 characters!*").queue();
				}
			}
		}
	}
}
