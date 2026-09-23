package com.satvik.url_shortner.util;

public class Base62 {

    private static String Characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static int Base = 62;

    public static String encode(long id){
        if(id == 0) return String.valueOf(Characters.charAt(0));

        StringBuilder sb = new StringBuilder();
        while (id>0){
        int remainder = (int) (id%Base);
        sb.append(Characters.charAt(remainder));
        id /= Base;
    }
        return sb.reverse().toString();
}

public static long decode(String shortCode){
    long result = 0;
    for(char c : shortCode.toCharArray()){
        result = result * Base + Characters.indexOf(c);
    }
    return result;
    }
}
