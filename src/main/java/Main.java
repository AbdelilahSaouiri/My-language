import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.print(">>>");
            var input=sc.nextLine();
            List<Lexer.Token> tokens = Lexer.tokenize(input);
            for (var token : tokens) {
                System.out.println(token.toString());
            }
        }
    }
}
