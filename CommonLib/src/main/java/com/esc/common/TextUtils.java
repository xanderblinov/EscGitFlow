package com.esc.common;

import java.util.HashSet;

/**
 * Date: 4/19/2015
 * Time: 1:59 PM
 *
 * @author xanderblinov
 */
public class TextUtils {
    public static boolean isEmpty(final String value) {
        return value == null || value.equals("");
    }

    public static boolean isEmpty(CharSequence string) {
        return string == null || string.length() == 0;
    }

    public static Character[] GetIntersection(String S1, String S2) {
        HashSet<Character> h1 = new HashSet<Character>(), h2 = new HashSet<Character>();
        for(int i = 0; i < S1.length(); i++)
        {
            h1.add(S1.charAt(i));
        }
        for(int i = 0; i < S2.length(); i++)
        {
            h2.add(S2.charAt(i));
        }
        h1.retainAll(h2);

        return h1.toArray(new Character[0]);
    }

    public static Character[] GetUnion(String S1, String S2) {
        HashSet<Character> h1 = new HashSet<Character>();
        for(int i = 0; i < S1.length(); i++)
        {
            h1.add(S1.charAt(i));
        }
        for(int i = 0; i < S2.length(); i++)
        {
            h1.add(S2.charAt(i));
        }

        return h1.toArray(new Character[0]);
    }
}
