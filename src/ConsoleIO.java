import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleIO {
    private final Scanner scanner = new Scanner(System.in);

    public void println(String text){
        System.out.println(text);;
    }

    public String readLine(String prompt){
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt, int min, int max){
        while(true){
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            try{
                int value = Integer.parseInt(s);
                if (value < min || value > max){
                    System.out.println("Please enter a number between " + min + " and " + max);
                    continue;
                }
                return value;
            } catch (NumberFormatException e){
                System.out.println("Invalid number. Try again");
            }
        }
    }

    public BigDecimal readBigDecimal(String prompt){
        while(true){
            System.out.println(prompt);
            String s = scanner.nextLine().trim().replace(",", ".");
            try{
                return new BigDecimal(s);
            } catch (NumberFormatException e){
                System.out.println("Invalid amount. Example: 13.50");
            }
        }
    }

}
