package converter;

import java.util.Scanner;

public class Selector {

    public Selector() {
        loop1();
    }

    private static final Scanner scanner = new Scanner(System.in);

    private void loop1() {
        while (true) {
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            String source = scanner.next();
            if ("/exit".equals(source)) {
                break;
            } else {
                String base = scanner.next();
                loop2(source, base);
            }
        }
    }

    private void loop2(String source, String base) {
        while (true) {
            System.out.print("Enter number in base " + source + " to convert to base " + base + " (To go back type /back) ");
            String number = scanner.next();
            if ("/back".equals(number)) {
                break;
            } else {
                Converter converter = new Converter(number, source, base);
                System.out.println("Conversion result: " + converter.toString());
            }
        }
    }
}
