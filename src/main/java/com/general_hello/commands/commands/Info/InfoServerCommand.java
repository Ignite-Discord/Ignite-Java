package com.general_hello.commands.commands.Info;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Utils.UtilString;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class InfoServerCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        Guild guild = ctx.getGuild();

        //Detects ID
        if(ctx.getArgs().size() > 0) {
            if("-m".equals(ctx.getArgs().get(0)) && ctx.getArgs().size() > 1 && ctx.getArgs().get(0).length() == 18)
                guild = ctx.getJDA().getGuildById(ctx.getArgs().get(0));
            else if(ctx.getArgs().get(0).length() == 18)
                guild = ctx.getJDA().getGuildById(ctx.getArgs().get(0));
        }


        if(guild == null) {
            ctx.getChannel().sendMessage("🛑 Cannot find this guild.\n"
                    + "Either I am not in this guild or the ID you provided is invalid.").queue();
            return;
        }

        String icon, name, id, owner, created, region, verify;
        int txtChannel, audioChannel, member, online = 0, human = 0, bot = 0;

        icon = guild.getIconUrl();

        /* Identity */
        name = guild.getName();
        id = guild.getId();
        owner = Objects.requireNonNull(guild.getOwner()).getAsMention();

        /* Channel */
        txtChannel = guild.getTextChannels().size();
        audioChannel = guild.getVoiceChannels().size();

        /* Time */
        created = UtilString.formatOffsetDateTime(guild.getTimeCreated());

        /* Member */
        List<Member> members = guild.getMembers();
        member = guild.getMemberCount();
        for(Member memberM : members)  {
            String status = memberM.getOnlineStatus().toString();
            if(status.equals(OnlineStatus.ONLINE.toString()))
                online ++;
            if(!memberM.getUser().isBot())
                human ++;
            else
                bot ++;
        }

        /* Other */
        verify = guild.getVerificationLevel().toString();
        region = guild.getRegion().toString();


        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(name, null, null).setColor(Color.blue).setThumbnail(icon).setTimestamp(Instant.now())
                .setFooter("Server Info", null);

        embed.addField("ID", id, true);
        embed.addField("Owner", owner, true);

        embed.addField("⏱ Created In", created, true);

        embed.addField("Channel", "Text `"+txtChannel+"` | Voice `"+audioChannel+"`", true);
        embed.addField("😎" + " Member", "User `"+member+"` |  `"+online+"`\nHuman `"+human+"` |  Bot `"+bot+"`", true);
        embed.addField("Other", "Region `" + region + "` | Verification `" + verify + "`", true);

        ctx.getChannel().sendMessageEmbeds(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "serverinfo";
    }

    @Override
    public String getHelp(String prefix) {
        return "Shows info about that server\n" +
                "Usage: `" + prefix + "serverinfo`\n" +
                "Parameter: `-h | [ID] | null`";
    }
}
