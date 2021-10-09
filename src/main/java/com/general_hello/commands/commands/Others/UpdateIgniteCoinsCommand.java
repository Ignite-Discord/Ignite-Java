package com.general_hello.commands.commands.Others;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.DefaultCommands.SheetsQuickstart;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Register.Data;
import com.general_hello.commands.commands.User.UserPhoneUser;
import com.general_hello.commands.commands.Utils.EmbedUtil;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UpdateIgniteCoinsCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {
        if (!ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById(888627140046749697L))) {
            ctx.getChannel().sendMessage("You don't have the `Manage Server permission` with you!").queue();
            return;
        }

        loadData();

        ctx.getChannel().sendMessageEmbeds(EmbedUtil.successEmbed("Successfully updated all users balance!")).queue();
    }

    @Override
    public String getName() {
        return "updatecoins";
    }

    @Override
    public String getHelp(String prefix) {
        return "Updates all the balances of all the users!";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.WALLET;
    }

    public static List<List<Object>> loadData() throws IOException {
        String APPLICATION_NAME = "Ignite bot";

        NetHttpTransport HTTP_TRANSPORT;
        // Build a new authorized API client service.
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Exception e) {
            return null;
        }
        final String spreadsheetId = "1tpgu2NVn7maZt81qhBI-sJqiDV7gwOXU5O6WIghwZhM";
        final String range = "ALL Igniters!B3:I131";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, SheetsQuickstart.JSON_FACTORY, SheetsQuickstart.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            System.out.println("Name, Major");
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                if (!row.get(0).toString().equals("Maxine")) {
                    String name = row.get(0).toString();
                    String[] split = name.split("\\s+");
                    int length = split.length;

                    if (length == 3) {
                        name = split[0] + " " + split[2];
                    } else {
                        name = split[0] + " " + split[1];
                    }

                    if (Data.realNameUserPhoneUserHashMap.containsKey(name)) {
                        System.out.println(name);
                        UserPhoneUser userPhoneUser = Data.realNameUserPhoneUserHashMap.get(name);
                        userPhoneUser.setBalance(Integer.parseInt(row.get(7).toString()));
                        Data.realNameUserPhoneUserHashMap.put(name, userPhoneUser);
                    }
                }
            }
        }

        return values;
    }
}
