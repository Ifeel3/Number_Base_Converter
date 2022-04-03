package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Converter {
    private String number;
    private int source;
    private int base;

    public Converter(String num, String source, String base) {
        this.number = num;
        this.source = Integer.parseInt(source);
        this.base = Integer.parseInt(base);
    }

    @Override
    public String toString() {
        String[] str = number.split("\\.");
        String result = new String("");
        if (str.length == 2) {
            result += mainPart(str[0]) + "." + fractionPart(str[1]);
        } else {
            result += mainPart(str[0]);
        }
        return result;
    }

    private String fractionPart(String number) {
        BigDecimal raw;
        if (source != 10) {
            raw = BigDecimal.ZERO;
            String[] splitted = number.split("");
            for (int i = 0; i < splitted.length; i++) {
                if (splitted[i].charAt(0) >= '1' && splitted[i].charAt(0) <= '9') {
                    raw = raw.add(BigDecimal.valueOf(charToInt(splitted[i].charAt(0)) * Math.pow(source, -i - 1)));
                } else if (splitted[i].charAt(0) >= 'a' && splitted[i].charAt(0) <= 'z') {
                    raw = raw.add(BigDecimal.valueOf(charToInt(splitted[i].charAt(0)) * Math.pow(source, -i - 1)));
                }
            }
        } else {
            raw = new BigDecimal("0." + number);
        }
        StringBuilder result = new StringBuilder("");
        if (base != 10) {
            for (int i = 0; i < 5 && raw.doubleValue() > 0; i++) {
                raw = raw.multiply(BigDecimal.valueOf(base));
                result.append(intToChar(raw.setScale(0, RoundingMode.DOWN).intValue()));
                raw = raw.subtract(raw.setScale(0, RoundingMode.DOWN));
            }
            if (result.length() < 5) {
                for (int i = result.length(); i < 5; i++) {
                    result.append('0');
                }
            }
        } else {
            return raw.toString().substring(2);
        }
        return result.toString();
    }

    private String mainPart(String number) {
        BigInteger raw;
        if (source != 10) {
            raw = BigInteger.ZERO;
            String[] splitted = number.toLowerCase().split("");
            for (int i = 0, j = splitted.length - 1; j >= 0; i++, j--) {
                if (splitted[j].charAt(0) >= '1' && splitted[j].charAt(0) <= '9') {
                    raw = raw.add(BigInteger.valueOf(charToInt(splitted[j].charAt(0)) * (long) Math.pow(source, i)));
                } else if (splitted[j].charAt(0) >= 'a' && splitted[j].charAt(0) <= 'z') {
                    raw = raw.add(BigInteger.valueOf(charToInt(splitted[j].charAt(0)) * (long) Math.pow(source, i)));
                }
            }
        } else {
            raw = new BigInteger(number);
        }
        StringBuilder result = new StringBuilder();
        if (base != 10) {
            while (raw.compareTo(BigInteger.ONE) >= 0) {
                BigInteger[] divAndRem = raw.divideAndRemainder(BigInteger.valueOf(base));
                if (divAndRem[1].intValue() < 10) {
                    result.append(intToChar(divAndRem[1].intValue()));
                } else {
                    result.append(intToChar(divAndRem[1].intValue()));
                }
                raw = divAndRem[0];
            }
            result.reverse();
        } else {
            return raw.toString();
        }
        return result.toString();
    }

    public static int charToInt(char ch) {
        int result = 0;
        if (ch >= '0' && ch <= '9') {
            result = ch - '0';
        }
        if (ch >= 'a' && ch <= 'z') {
            result = ch - 'a' + 10;
        }
        return result;
    }

    public static char intToChar(int integer) {
        char result = 0;
        if (integer >= 0 && integer <= 9) {
            result = (char)(integer + '0');
        }
        if (integer >= 10 && integer <= 35) {
            result = (char)(integer - 10 + 'a');
        }
        return result;
    }
}
