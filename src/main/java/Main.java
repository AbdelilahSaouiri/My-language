import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.print(">>>");
            var input=sc.nextLine();
            List<Lexer.Token> tokens = Lexer.tokenize(input);
            Parser parser=new Parser(tokens);
            Expr ast = parser.expr();
            if (!parser.isEnd()) throw new IllegalStateException("tokens restants");

            double ans = Parser.eval(ast, Map.of("x", 3.0));
            System.out.println(ans);

        }
    }
}
