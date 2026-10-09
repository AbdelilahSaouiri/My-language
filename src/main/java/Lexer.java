import java.util.ArrayList;
import java.util.List;

public class Lexer {

    enum TokenType {PLUS, MINUS, NUMBER,INDENT,STAR,SLASH,LPAREN,RPAREN}

     record Token(TokenType type, String value) {}

    static List<Token> tokenize(String input){
        List<Token> tokens=new ArrayList<>();
         int i=0;

         while(i<input.length()){
             var c=input.charAt(i);
             if(c==' ') i++;
             else if(c=='+') {
                 tokens.add(new Token(TokenType.PLUS, "+"));
                 i++;
             }
             else if(c=='-') {
                 tokens.add(new Token(TokenType.MINUS, "-"));
                 i++;
             }
             else if(c=='(') {
                 tokens.add(new Token(TokenType.LPAREN, "("));
                 i++;
             }
             else if(c==')') {
                 tokens.add(new Token(TokenType.RPAREN, ")")) ;
                 i++;
             }
             else if(c=='*') {
                 tokens.add(new Token(TokenType.STAR, "*"));
                 i++;
             }
             else if(c=='/') {
                 tokens.add(new Token(TokenType.SLASH, "/"));
                 i++;
             }
             else if(c=='\n') {
                 tokens.add(new Token(TokenType.INDENT, "\n"));
                 i++;
             }
             else if(Character.isDigit(c) || c=='.') {
                 int start = i;
                 while (i < input.length() && (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')) {
                     i++;
                 }
                 tokens.add(new Token(TokenType.NUMBER, input.substring(start, i)));
             }else if(Character.isLetter(c) || c=='_') {
                 int start = i;
                 while(i<input.length() && (Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i)=='_')) i++;
                 tokens.add(new Token(TokenType.NUMBER,input.substring(start,i)));
             }
             else throw new RuntimeException("Invalid character: "+c);

         }

        return tokens;
    }

}
