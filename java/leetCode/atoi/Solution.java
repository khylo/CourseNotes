package atoi;
/**
 *
 Whitespace: Ignore any leading whitespace (" ").
 Signedness: Determine the sign by checking if the next character is '-' or '+', assuming positivity if neither present.
 Conversion: Read the integer by skipping leading zeros until a non-digit character is encountered or the end of the string is reached. If no digits were read, then the result is 0.
 Rounding: If the integer is out of the 32-bit signed integer range [-231, 231 - 1], then round the integer to remain in the range. Specifically, integers less than -231 should be rounded to -231, and integers greater than 231 - 1 should be rounded to 231 - 1.
 Return the integer as the final result.



 */
class Solution implements SolutionInf {
    public int myAtoi(String s) {
        int c = 0;
        // Signedness: Determine the sign by checking if the next character is '-' or '+', assuming positivity if neither present.
        boolean positive = true;
        int ans = 0;
        //boolean nums = c<s.length()-1;
        int stage = 0; // 0: whitespace, 1: signedness, 2: conversion, 3: rounding;
        while(c<s.length()) {
            // Whitespace: Ignore any leading whitespace (" ").
            if(stage==0 && s.substring(c, c + 1).equals(" ")) {
                c++;
                continue;
            }
            if (stage==0 && s.substring(c, c + 1).equals("-")) {
                positive = false;
                c++;
                stage=2;
                continue;
            } else if (stage==0 &&s.substring(0, 1).equals("+")){
                positive = true;
                c++;
                stage=2;
                continue;
            }
            // Conversion: Read the integer by skipping leading zeros until a non-digit character is
            // encountered or the end of the string is reached. If no digits were read, then the result
            // is 0.
            //System.out.println(String.format("s=%s, c=%d, stage=%d, ans=%d ch=%s",s,c,stage,ans,s.charAt(c)));
            char ch = s.charAt(c);
            if(stage<2 && (ch >='0' && ch <='9')) {
                stage = 2;
            }
            if(stage==2 ) {
                if (ch == '0') {
                    c++;
                    continue;
                }else {
                    stage = 3;
                }
            }
            if(stage==3 && (ch >='0' && ch <='9')) {
                if(ans>=((Integer.MAX_VALUE/10) - (ch-'0'))) {
                    return positive ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                }
                ans = ans*10+(ch-'0');
                c++;

            }else{
                return positive?ans:-ans;
            }
        }
        return positive?ans:-ans;
    }
}