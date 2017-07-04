package net.gaminghunter.pokebattle.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Luca on 15.06.2017.
 */
public class Utils {

    //NoAI
    private static String serverVersion;
    private static Method getHandle;
    private static Method getNBTTag;
    private static Class<?> nmsEntityClass;
    private static Class<?> nbtTagClass;
    private static Method c;
    private static Method setInt;
    private static Method f;

    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max+1);
    }

    public static String getDurationString(int seconds) {

        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }

    private static String twoDigitString(int number) {
        if (number == 0) {
            return "00";
        }
        if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

    public static void setAiEnabled(Entity entity, boolean enabled) {
        try {
            if (serverVersion == null) {
                String name = Bukkit.getServer().getClass().getName();
                String[] parts = name.split("\\.");
                serverVersion = parts[3];
            }
            if (getHandle == null) {
                Class<?> craftEntity = Class.forName("org.bukkit.craftbukkit." + serverVersion + ".entity.CraftEntity");
                getHandle = craftEntity.getDeclaredMethod("getHandle");
                getHandle.setAccessible(true);
            }
            Object nmsEntity = getHandle.invoke(entity);
            if (nmsEntityClass == null) {
                nmsEntityClass = Class.forName("net.minecraft.server." + serverVersion + ".Entity");
            }
            if (getNBTTag == null) {
                getNBTTag = nmsEntityClass.getDeclaredMethod("getNBTTag");
                getNBTTag.setAccessible(true);
            }
            Object tag = getNBTTag.invoke(nmsEntity);
            if (nbtTagClass == null) {
                nbtTagClass = Class.forName("net.minecraft.server." + serverVersion + ".NBTTagCompound");
            }
            if (tag == null) {
                tag = nbtTagClass.newInstance();
            }
            if (c == null) {
                c = nmsEntityClass.getDeclaredMethod("c", nbtTagClass);
                c.setAccessible(true);
            }
            c.invoke(nmsEntity, tag);
            if (setInt == null) {
                setInt = nbtTagClass.getDeclaredMethod("setInt", String.class, Integer.TYPE);
                setInt.setAccessible(true);
            }
            int value = enabled ? 0 : 1;
            setInt.invoke(tag, "NoAI", value);
            if (f == null) {
                f = nmsEntityClass.getDeclaredMethod("f", nbtTagClass);
                f.setAccessible(true);
            }
            f.invoke(nmsEntity, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void winGame(Player player) {

    }

}
