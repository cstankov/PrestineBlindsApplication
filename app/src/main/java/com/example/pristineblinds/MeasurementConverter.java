package com.example.pristineblinds;

public class MeasurementConverter {

    public MeasurementConverter() {
    }

    // Found at : https://www.geeksforgeeks.org/convert-given-decimal-number-into-an-irreducible-fraction/
    private long gcd(long a, long b) {
        if (a == 0)
            return b;
        else if (b == 0)
            return a;
        if (a < b)
            return gcd(a, b % a);
        else
            return gcd(b, a % b);
    }

    // Found at : https://www.geeksforgeeks.org/convert-given-decimal-number-into-an-irreducible-fraction/
    public String decimalToFraction(double number) {
        double intVal = Math.floor(number);
        double fVal = number - intVal;
        final long pVal = 1000000000;
        long gcdVal = gcd(Math.round(
                fVal * pVal), pVal);

        long num = Math.round(fVal * pVal) / gcdVal;
        long deno = pVal / gcdVal;

        return (long)(intVal * deno) + num + "/" + deno;
    }

    public Double roundHeightRemainder(String height_remainder_string, Double heightRemainder){
        Double roundedHeightRemainder;
        if(!roundSpecialCases(height_remainder_string)){
            Double heightR = heightRemainder * 8;
            heightR = Math.floor(heightR);
            roundedHeightRemainder = heightR / 8;
        }else{
            Double heightR = heightRemainder * 8;
            heightR = Math.ceil(heightR);
            roundedHeightRemainder = heightR / 8;
        }
        return roundedHeightRemainder;
    }

    public Double roundWidthRemainder(String roundedWidthRemainderString, Double widthRemainder){
        Double roundedWidthRemainder;
        if(!roundSpecialCases(roundedWidthRemainderString)){
            Double widthR = widthRemainder * 8;
            widthR = Math.floor(widthR);
            roundedWidthRemainder = widthR / 8;
        }else{
            Double widthR = widthRemainder * 8;
            widthR = Math.ceil(widthR);
            roundedWidthRemainder = widthR / 8;
        }
        return roundedWidthRemainder;
    }

    private boolean roundSpecialCases(String remainder_string){
        boolean foundSpecialCase = false;

        if(remainder_string.equals("3/32") || remainder_string.equals("7/32") ||
                remainder_string.equals("11/32") || remainder_string.equals("15/32") ||
                remainder_string.equals("19/32") || remainder_string.equals("23/32") ||
                remainder_string.equals("27/32") || remainder_string.equals("31/32")){
            foundSpecialCase = true;
        }
        return foundSpecialCase;
    }

}
