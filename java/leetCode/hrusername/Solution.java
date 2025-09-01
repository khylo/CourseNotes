package hrusername;

import java.util.Scanner;

public class Solution {
    class UsernameValidator{
        public static final String regularExpression = "^[[a-z][A-Z]]{1}[[a-z][A-Z][0-9]_]{7,29}";
        //public static final String regularExpression = "^([[a-][A-Z]]{1}[[a-z][A-Z][0-9]_]{7,29}";
    }

    private static final Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        int n = Integer.parseInt(scan.nextLine());
        while (n-- != 0) {
            String userName = scan.nextLine();

            if (userName.matches(UsernameValidator.regularExpression)) {
                System.out.println(userName+" = Valid");
            } else {
                System.out.println(userName+" = Invalid");
            }
        }
    }
}
