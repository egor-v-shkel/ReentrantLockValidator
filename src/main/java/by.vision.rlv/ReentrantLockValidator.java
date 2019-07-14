package by.vision.rlv;

import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class ReentrantLockValidator {

    private static int numToDelete;
    private static char deletionChar;
    private static String string;

    public static void main(String[] args) {

        //String s = "{{}{{}}{}{}}{}";
        //String s = "{{}{{}}{}{}}{{{}}}{}";
        String s = "x{x}x}x{x}x}x";
        //String s = "{}}{}}";
        //String s = "x{x}x}x{x}x}x";
        //String s = "{}x}x}";

        ReentrantLockValidator.validate(s).forEach(x -> {
            final String collect = String.join(" ", x.split(""));
            System.out.println(collect);
        });

    }

    public static Set<String> validate(String input) {

        Set<String> set = new TreeSet<>();
        final StringBuilder sb = new StringBuilder(input);


        edgeCases(sb);

        string = sb.toString();

        numToDelete = unnecessaryBrackets(string);

        if (sb.indexOf("{") == -1 || sb.indexOf("}") == -1) return set;

        recursion(string, 0, set);

/*
        for (int i = 0; i < s.length(); i++) {

            String s2 = s.substring(0, i) + s.substring(i + 1);

            for (int j = i; j < s2.length(); j++) {

                String s3 = s2.substring(0, j) + s2.substring(j + 1);

                for (int k = j; k < s3.length(); k++) {

                    String s4 = s3.substring(0, k) + s3.substring(k + 1);
                    if (unclosedBrackets(s4)) set.add(s4);
                }

            }

        }
*/
        return set;

    }

    private static void edgeCases(StringBuilder sb) {
        while (sb.indexOf("}") > -1 && sb.indexOf("}") < sb.indexOf("{")) {
            final int index = sb.indexOf("}");
            sb.deleteCharAt(index);
        }
        while (sb.lastIndexOf("}") > -1 && sb.lastIndexOf("{") > sb.lastIndexOf("}")) {
            final int index = sb.lastIndexOf("{");
            sb.deleteCharAt(index);
        }

    }

    private static int unnecessaryBrackets(String s) {
        int counter;

        int openBracketsCount = 0;
        int closedBracketsCount = 0;

        for (char c :
                s.toCharArray()) {
            if (c == '{') openBracketsCount++;
            if (c == '}') closedBracketsCount++;
        }

        counter = openBracketsCount - closedBracketsCount;

        if (counter > 0) {
            deletionChar = '{';
        } else if (counter < 0) {
            counter = Math.abs(counter);
            deletionChar = '}';
        }

        return counter;

    }

    private static void recursion(String s, int index, Set<String> set) {

        final boolean minSize = (s.length() == string.length() - numToDelete);
        if (bracketsClosed(s) && minSize) set.add(s);

        for (int i = index; i < s.length(); i++) {
            final char c = s.charAt(i);
            if (deletionChar == c) recursion(s.substring(0, i) + s.substring(i + 1), i, set);
        }

    }

    private static boolean bracketsClosed(String s) {

        Stack<Character> stack = new Stack<>();

        for (char c :
                s.toCharArray()) {
            if (c != '{' && c != '}') continue;
            if (c == '{') {
                stack.push(c);
                continue;
            }
            if (stack.empty()) {
                return false;
            } else stack.pop();
        }

        return stack.empty();
    }

}