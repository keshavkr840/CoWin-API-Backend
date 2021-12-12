package com.example.portal.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralUtil {
    public static boolean spellCheck(String string){
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        boolean b = m.find();
        return b;
    }
    final static String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";




    public static String genRandomUsername() {
        Set<String> identifiers = new HashSet<String>();
        java.util.Random rand = new java.util.Random();
        StringBuilder builder = new StringBuilder();
        while(builder.toString().length() == 0) {

            int length = rand.nextInt(5)+5;
            for(int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if(identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }
    public static boolean pwdCheck(String password)
    {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&*]).{8,15}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static String tokenGenerator(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    // Password Generation for 8-15 length and atleast one numeric,aplhabet and special character
    public static String genRandomPassword(){
        char[] SYMBOLS = "@#$%^&*".toCharArray();
        char[] LOWERCASE = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] NUMBERS = "0123456789".toCharArray();
        char[] ALL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%^&*".toCharArray();
        Random rand = new Random();
        int pwdlen =  rand.nextInt(8) + 8;
        char[] password = new char[pwdlen];
        password[0] = LOWERCASE[rand.nextInt(LOWERCASE.length)];
        password[1] = UPPERCASE[rand.nextInt(UPPERCASE.length)];
        password[2] = NUMBERS[rand.nextInt(NUMBERS.length)];
        password[3] = SYMBOLS[rand.nextInt(SYMBOLS.length)];

        for (int i = 4; i < pwdlen; i++) {
            password[i] = ALL_CHARS[rand.nextInt(ALL_CHARS.length)];
        }

        //swapping random positions
        for (int i = 0; i < pwdlen; i++) {
            int randomPosition = rand.nextInt(pwdlen);
            char temp = password[i];
            password[i] = password[randomPosition];
            password[randomPosition] = temp;
        }
        return new String(password);
    }
    public static String getDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String strDate= formatter.format(date);
        return strDate;
    }
}
