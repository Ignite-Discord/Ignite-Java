package com.general_hello.commands.commands.RankingSystem;

import com.general_hello.commands.commands.GetData;
import net.dv8tion.jda.api.entities.User;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.function.Function;

public class LevelPointManager{

    private static final int POINTS_PER_MESSAGE = 5;
    //in seconds
    private static final int DELAY = 1;
    private static final Function<Long, Long> CALCULATE_LEVEL = ep -> (long) (1 / (float) (8) * Math.sqrt(ep));
    private static final Function<Long, Long> CALCULATE_EP = level -> (long) 64 * (long) Math.pow(level, 2);

    public static final HashMap<User, OffsetDateTime> accessMap = new HashMap<>();

    public static long calculateLevel(User member) throws SQLException {
        return calculateLevel(GetData.getLevelPoints(member));
    }

    public static long calculateLevel(long levelPoints){
        return CALCULATE_LEVEL.apply(levelPoints);
    }

    public static long calculateLevelMin(long level){
        return CALCULATE_EP.apply(level);
    }

    public static long calculateLevelMax(long level){
        return CALCULATE_EP.apply(level + 1);
    }

    public static LevelPointCard getLevelPointCard(User member) throws SQLException {
        return new LevelPointCard(member);
    }

    public static void trackMember(User member) {
        try {
            OffsetDateTime min = OffsetDateTime.MIN;

            accessMap.put(member, min);
        } catch (Exception ignore) {

        }
    }

    public static synchronized void feed(User member){
        try{
            if(!accessMap.containsKey(member)){
                System.out.println("Added member :)");
                trackMember(member);
            }

            OffsetDateTime last = accessMap.get(member);
            if(OffsetDateTime.now().isBefore(last.plusMinutes(DELAY))){
                System.out.println("Did not add xp to " + member.getName() + " due to delay!");
                return;
            }


            accessMap.put(member, OffsetDateTime.now());

            Thread.sleep(500);
            GetData.setLevelPoints(member, GetData.getLevelPoints(member) + POINTS_PER_MESSAGE);
            System.out.println("Added xp to " + member.getName() + "!");
        }
        catch(Exception ignore){
        }
    }

    public static synchronized void feed(User member, long xpToAdd){
        try{
            if(!accessMap.containsKey(member)){
                System.out.println("Added member :)");
                trackMember(member);
            }

            Thread.sleep(10);
            GetData.setLevelPoints(member, GetData.getLevelPoints(member) + xpToAdd);
            System.out.println("Added xp to " + member.getName() + "!");
        }
        catch(Exception ignore){
        }
    }
}
