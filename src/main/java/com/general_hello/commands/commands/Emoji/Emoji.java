package com.general_hello.commands.commands.Emoji;

import com.vdurmont.emoji.EmojiParser;

import java.util.ArrayList;
import java.util.Arrays;

public class Emoji {
    /* General */
    public final static String SUCCESS = EmojiParser.parseToUnicode(":white_check_mark:");
    public final static String ERROR = "<a:cancel:863204248657461298>";
    public final static String YES = EmojiParser.parseToUnicode(":o:");
    public final static String NO = EmojiParser.parseToUnicode(":x:");
    public final static String IN = EmojiParser.parseToUnicode(":inbox_tray:");
    public final static String OUT = EmojiParser.parseToUnicode(":outbox_tray:");
    public final static String UP = EmojiParser.parseToUnicode(":arrow_up:");
    public final static String DOWN = EmojiParser.parseToUnicode(":arrow_down:");
    public final static String RIGHT = EmojiParser.parseToUnicode(":arrow_right:");
    public final static String LEFT = EmojiParser.parseToUnicode(":arrow_left:");
    public final static String INVITE = EmojiParser.parseToUnicode(":postbox:");
    public final static String INFORMATION = EmojiParser.parseToUnicode(":information_source:");
    public final static String BAN = EmojiParser.parseToUnicode(":hammer:");

    /* Faces */
    public final static String FACE_TONGUE = EmojiParser.parseToUnicode(":stuck_out_tongue:");
    public final static String FACE_BLUSH = EmojiParser.parseToUnicode(":blush:");

    /* Hand */
    public final static String HAND_OK = EmojiParser.parseToUnicode(":ok_hand:");
    public final static String HAND_WAVE = EmojiParser.parseToUnicode(":wave:");
    public final static String HAND_RAISE = EmojiParser.parseToUnicode(":raised_hand:");
    public final static String HAND_HORN = EmojiParser.parseToUnicode(":horns_sign:");
    public final static String HAND_MUSCLE = EmojiParser.parseToUnicode(":muscle:");
    public final static String THUMB_UP = EmojiParser.parseToUnicode(":thumbsup:");
    public final static String THUMB_DOWN = EmojiParser.parseToUnicode(":thumbsdown:");
    public final static String POINT_UP = EmojiParser.parseToUnicode(":point_up:");
    public final static String POINT_DOWN = EmojiParser.parseToUnicode(":point_down:");
    public final static String POINT_LEFT = EmojiParser.parseToUnicode(":point_left:");
    public final static String POINT_RIGHT = EmojiParser.parseToUnicode(":point_right:");
    public final static String PRAY = EmojiParser.parseToUnicode(":pray:");

    /* Numbers */
    public final static String ONE = EmojiParser.parseToUnicode(":one:");
    public final static String TWO = EmojiParser.parseToUnicode(":two:");
    public final static String THREE = EmojiParser.parseToUnicode(":three:");
    public final static String FOUR = EmojiParser.parseToUnicode(":four:");
    public final static String FIVE = EmojiParser.parseToUnicode(":five:");
    public final static String SIX = EmojiParser.parseToUnicode(":six:");
    public final static String SEVEN = EmojiParser.parseToUnicode(":seven:");
    public final static String EIGHT = EmojiParser.parseToUnicode(":eight:");
    public final static String NINE = EmojiParser.parseToUnicode(":nine:");
    public final static String ZERO = EmojiParser.parseToUnicode(":zero:");
    public final static String HUNDRED = EmojiParser.parseToUnicode(":100:");

    /* Information Commands */
    public final static String ENVELOPE = EmojiParser.parseToUnicode(":incoming_envelope:");
    public final static String PING = EmojiParser.parseToUnicode(":ping_pong:");
    public final static String HEART_BEAT = EmojiParser.parseToUnicode(":heartbeat:");
    public final static String STOPWATCH = EmojiParser.parseToUnicode(":stopwatch:");
    public final static String STATUS = EmojiParser.parseToUnicode(":vertical_traffic_light:");
    public final static String STATISTIC = EmojiParser.parseToUnicode(":bar_chart:");
    public final static String GUILDS = EmojiParser.parseToUnicode(":card_file_box:");
    public final static String SHARDS = EmojiParser.parseToUnicode(":file_cabinet:");
    public final static String TEXT = EmojiParser.parseToUnicode(":speech_balloon:");
    public final static String SPY = EmojiParser.parseToUnicode(":spy:");

    /* Utility Commands */
    /* NumberCommand */
    public final static String NUMBER = EmojiParser.parseToUnicode(":1234:");
    public final static String PRINT = EmojiParser.parseToUnicode(":printer:");
    public final static String ROLL = EmojiParser.parseToUnicode(":game_die:");

    /* WeatherCommand */
    public final static String TEMP = EmojiParser.parseToUnicode(":thermometer:");
    public final static String WIND = EmojiParser.parseToUnicode(":dash:");
    /* Condition */
    public final static String SUNNY = EmojiParser.parseToUnicode(":sunny:");
    public final static String CLOUD = EmojiParser.parseToUnicode(":cloud:");
    public final static String CLOUD_PART = EmojiParser.parseToUnicode(":white_sun_small_cloud:");
    public final static String CLOUDY = EmojiParser.parseToUnicode(":white_sun_behind_cloud:");
    public final static String CLOUDY_RAIN = EmojiParser.parseToUnicode(":white_sun_behind_cloud_rain:");
    public final static String CLOUD_RAIN = EmojiParser.parseToUnicode(":cloud_rain:");
    public final static String CLOUD_THUNDER_RAIN = EmojiParser.parseToUnicode(":thunder_cloud_rain:");
    public final static String CLOUD_TORNADO = EmojiParser.parseToUnicode(":cloud_tornado:");
    public final static String SNOW = EmojiParser.parseToUnicode(":cloud_snow:");
    public final static String WINDY = EmojiParser.parseToUnicode(":blowing_wind:");
    public final static String SNOWMAN = EmojiParser.parseToUnicode(":snowing_snowman:");
    public final static String SWEAT = EmojiParser.parseToUnicode(":sweat:");
    public final static String PRESS = EmojiParser.parseToUnicode(":compression:");
    public final static String EYES = EmojiParser.parseToUnicode(":eyes:");
    /* EmojiCommmand */
    public final static String ABC = EmojiParser.parseToUnicode(":abc:");
    public final static String ABCD = EmojiParser.parseToUnicode(":abcd:");
    public final static String VS = EmojiParser.parseToUnicode(":vs:");
    public final static String COOL = EmojiParser.parseToUnicode(":cool:");
    public final static String OK = EmojiParser.parseToUnicode(":ok:");
    public final static String SYMBOLS = EmojiParser.parseToUnicode(":symbols:");
    public final static String NEW_WORD = EmojiParser.parseToUnicode(":new:");
    public final static String FREE = EmojiParser.parseToUnicode(":free:");
    public final static String MARK_QUESTION = EmojiParser.parseToUnicode(":grey_question:");
    public final static String MARK_EXCLAMATION = EmojiParser.parseToUnicode(":exclamation:");
    public final static String MARK_HASH = EmojiParser.parseToUnicode(":hash:");
    public final static String MARK_ASTERISK = EmojiParser.parseToUnicode(":keycap_asterisk:");
    public final static String MARK_PLUS_SIGN = EmojiParser.parseToUnicode(":heavy_plus_sign:");
    public final static String MARK_MINUS_SIGN = EmojiParser.parseToUnicode(":heavy_minus_sign:");
    public final static String MARK_DEVIDE_SIGN = EmojiParser.parseToUnicode(":heavy_division_sign:");
    public final static String DOT = EmojiParser.parseToUnicode(":black_circle_for_record:");
    public final static String NOTES = EmojiParser.parseToUnicode(":notes:");

    //custom emoji
    public final static String DISCORD_BOT = "<:discord_bot:862895574960701440>";
    public final static String NITRO = "<a:nitro:870863947241230396>";
    public final static String BABY_YODA = EmojiParser.parseToUnicode("<a:babyyoda:866105061665669140>");
    public final static String LIKE = EmojiParser.parseToUnicode("<a:like:866105060140646410>");
    public final static String BOOK = EmojiParser.parseToUnicode("<a:bookmoving:866105059851239444>");
    public final static String THANKS = EmojiParser.parseToUnicode("<a:thanks:863989523461177394>");
    public final static String QUESTION = EmojiParser.parseToUnicode("<a:question:863989523368247346>");
    public final static String VERIFY = EmojiParser.parseToUnicode("<a:verify:863204252188672000>");
    public final static String CANCEL = EmojiParser.parseToUnicode("<a:cancel:863204248657461298>");
    public final static String MOD = EmojiParser.parseToUnicode("<a:mod:862898484041482270>");
    public final static String INFO = EmojiParser.parseToUnicode("<a:info:870871190217060393>");
    public final static String USER = EmojiParser.parseToUnicode("<a:user:862895295239028756>");
    public final static String LOADING = EmojiParser.parseToUnicode("<a:loading:870870083285712896>");
    public final static String ARROW_POINTING_RIGHT = EmojiParser.parseToUnicode("<a:arrow_1:862525611465113640>");
    public final static String ARROW_POINTING_LEFT = EmojiParser.parseToUnicode("<a:arrow_2:862525611443879966>");
    public final static String USER_COIN = EmojiParser.parseToUnicode("<:userphonecoin:872996023570661397>");
    public final static String BLACKJACK = EmojiParser.parseToUnicode("<a:bjk:891148784967491604>");
    public final static String FAQ = EmojiParser.parseToUnicode("<a:faq:891179078911143976>");
    public final static String GAME = EmojiParser.parseToUnicode("<a:games:891146612016025630>");
    public final static String MEME = EmojiParser.parseToUnicode("<a:meme:891179097546453024>");
    public final static String UNO = EmojiParser.parseToUnicode("<a:uno:891148764537049088>");
    public final static String MUSIC = EmojiParser.parseToUnicode("<a:music:891148744408584192>");
    public final static String WALLET = EmojiParser.parseToUnicode("<a:wallet:891179114470465566>");

    /* Server Emotes */
    public final static String GUILD_ONLINE = "🟢";
    public final static String GUILD_IDLE = "🌙";
    public final static String GUILD_DND = "🔴";
    public final static String GUILD_OFFLINE = "⚫";
    public final static String GUILD_STREAMING = "🟣";

    public final static String CHECK = "<a:verify:863204252188672000>";
    public final static String UNCHECK = "<:xmark:314349398824058880>";
    public final static String NOCHECK = "<:empty:314349398723264512>";

    //chess emoji
    public final static String BBISHOP = "<:bbishop:880972626053439528>";
    public final static String BQUEEN = "<:bqueen:880972625889861672>";
    public final static String BKNIGHT = "<:bknight:880972625722105937>";
    public final static String BPAWN = "<:bpawn:880972625680171038>";
    public final static String BKING = "<:bking:880972625663381575>";
    public final static String WKING = "<:wking:880972625642422283>";
    public final static String WBISHOP = "<:wbishop:880972625617240124>";
    public final static String WKNIGHT = "<:wknight:880972625453678632>";
    public final static String WQUEEN = "<:wqueen:880972625365565450>";
    public final static String BROOK = "<:brook:880972625348796467>";
    public final static String WPAWN = "<:wpawn:880972625302683709>";
    public final static String WROOK = "<:wrook:880972624824512513>";

    public static EmojiObject customEmojiToEmote(String customEmoji) {
        customEmoji = customEmoji.replace("<", "");
        customEmoji = customEmoji.replace(">", "");

        String[] split = customEmoji.split(":");
        ArrayList<String> splitList = new ArrayList<>(Arrays.asList(split));
        boolean isAnimate = false;
        if (split[0].equals("a")) {
            isAnimate = true;
        }

        System.out.println(splitList);

        return new EmojiObject(splitList.get(1), isAnimate, Long.parseLong(splitList.get(2)));
    }
    public static String stringToEmoji(String input)
    {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++)
        {
            String letters = input.substring(i,i+1);
            char letterc = input.charAt(i);

            /*
             * Number More than ONE digit
             */
            //1234
            if(input.length() >= i+4 && "1234".equals(input.substring(i,i+4))) {
                output.append(Emoji.NUMBER);
                i+=3;
                continue;
            }
            //100
            if(input.length() >= i+3 && "100".equals(input.substring(i,i+3))) {
                output.append(HUNDRED);
                i+=2;
                continue;
            }

            /*
             * Character more than ONE digit
             */
            //ABCD or ABCD
            if(input.length() >= i+4 && "abcd".equalsIgnoreCase(input.substring(i,i+4))) {
                output.append(Emoji.ABCD);
                i+=3;
                continue;
            }
            //ABC or ABC
            else if(input.length() >= i+3 && "abc".equalsIgnoreCase(input.substring(i,i+3))) {
                output.append(Emoji.ABC);
                i+=2;
                continue;
            }
            if(input.length() >= i+2 && "vs".equalsIgnoreCase(input.substring(i,i+2))) {
                output.append(Emoji.VS);
                i+=1;
                continue;
            }
            if(input.length() >= i+5 && "music".equalsIgnoreCase(input.substring(i,i+5))) {
                output.append(Emoji.NOTES);
                i+=4;
                continue;
            }
            if(input.length() >= i+4 && "cool".equalsIgnoreCase(input.substring(i,i+4))) {
                output.append(Emoji.COOL);
                i+=3;
                continue;
            }
            if(input.length() >= i+3 && "new".equalsIgnoreCase(input.substring(i,i+3))) {
                output.append(Emoji.NEW_WORD);
                i+=2;
                continue;
            }
            if(input.length() >= i+4 && "free".equalsIgnoreCase(input.substring(i,i+4))) {
                output.append(Emoji.FREE);
                i+=3;
                continue;
            }
            if(input.length() >= i+2 && "ok".equalsIgnoreCase(input.substring(i,i+2))) {
                output.append(Emoji.OK);
                i+=1;
                continue;
            }
            if(input.length() >= i+2 && ".n".equalsIgnoreCase(input.substring(i,i+2))) {
                output.append("\n");
                i+=1;
                continue;
            }

            /*
             * Check One Letter at a Time
             */
            if(Character.isAlphabetic(letterc)) {
                output.append(lettersToEmoji(letters));
            }
            else if(Character.isDigit(letterc)) {
                output.append(numToEmoji(Integer.parseInt(letters)));
            }
            //Spacing
            else if(Character.isWhitespace(letterc)) {
                output.append(" ");
            }
            else if(!Character.isAlphabetic(letterc)) {
                switch(letters) {
                    case ".":
                        output.append(Emoji.DOT);
                        break;
                    case "?":
                        output.append(Emoji.MARK_QUESTION);
                        break;
                    case "!":
                        output.append(Emoji.MARK_EXCLAMATION);
                        break;
                    case "#":
                        output.append(Emoji.MARK_HASH);
                        break;
                    case "*":
                        output.append(Emoji.MARK_ASTERISK);
                        break;
                    case "&":
                    case "%":
                        output.append(Emoji.SYMBOLS);
                        break;
                    case "+":
                        output.append(Emoji.MARK_PLUS_SIGN);
                        break;
                    case "-":
                        output.append(Emoji.MARK_MINUS_SIGN);
                        break;
                    case "/":
                        output.append(Emoji.MARK_DEVIDE_SIGN);
                        break;
                    default:
                        break;
                }
            }
        }
        return output.toString();
    }

    /**
     * Change the letter(s) into a String of emojis
     * @param input the letter(s) to be change to emoji
     * @return String of letter(s) in emojis form
     */
    public static String lettersToEmoji(String input)
    {
        String output = ":regional_indicator_" + input.toLowerCase() + ":";
        return output;
    }

    /**
     * Change the input NUMBER into a String of emojis
     * @param digit the NUMBER to be change to emojis
     * @return String of a NUMBER in emojis from
     */
    public static String numToEmoji(int digit)
    {
        String output = "";
        switch(digit) {
            case 1:
                output += Emoji.ONE;
                break;
            case 2:
                output += Emoji.TWO;
                break;
            case 3:
                output += Emoji.THREE;
                break;
            case 4:
                output += Emoji.FOUR;
                break;
            case 5:
                output += Emoji.FIVE;
                break;
            case 6:
                output += Emoji.SIX;
                break;
            case 7:
                output += Emoji.SEVEN;
                break;
            case 8:
                output += Emoji.EIGHT;
                break;
            case 9:
                output += Emoji.NINE;
                break;
            default:
                output += Emoji.ZERO;
                break;
        }

        return output;
    }
}
