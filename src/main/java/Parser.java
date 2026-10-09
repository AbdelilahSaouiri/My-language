import java.util.List;
import java.util.Map;


interface Expr {};
record Num(int value) implements Expr {};
record Var(String name) implements Expr {};
record Binary(Expr left, char op,Expr right) implements Expr {};


public  class Parser {

    private final List<Lexer.Token> tokens;
    private int curr_position=0;

    public Parser(List<Lexer.Token> tokens) {
        this.tokens = tokens;
    }

    private Lexer.Token peek(){
        return curr_position < tokens.size() ? tokens.get(curr_position):null;
    }

    private Lexer.Token next(){
        return curr_position <tokens.size() ? tokens.get(curr_position++):null;
    }

    public boolean isEnd(){
        return curr_position >= tokens.size();
    }



    Expr factor(){
         var token=next();
         if(token==null) throw new RuntimeException("Unexpected end of input");
         return switch (token.type()){
             case NUMBER -> new Num(Integer.parseInt(token.value()));
             case INDENT -> new Var(token.value());
             case LPAREN -> {
                 Expr expr=expr();
                 var close=next();
                 if (close == null || close.type() != Lexer.TokenType.RPAREN)
                     throw new IllegalStateException("')' attendu");
                 yield expr;
             }
             default -> throw  new IllegalArgumentException("Invalid token: "+token);
         };
    }

    Expr term(){
        Expr left=factor();
        while(peek()!=null && (peek().type()==Lexer.TokenType.STAR || peek().type()==Lexer.TokenType.SLASH)){
         char op=next().value().charAt(0);
         left=new Binary(left,op,factor());
        }
           return left;
    }

    Expr expr() {
        Expr left = term();
        while (peek() != null && (peek().type() == Lexer.TokenType.PLUS || peek().type() == Lexer.TokenType.MINUS)) {
            char op = next().value().charAt(0);
            left = new Binary(left, op, term());
        }
        return left;
    }

    static double eval(Expr expr, Map<String,Double> env){
        return switch(expr){
            case Num num -> num.value();
            case Var v ->{
                Double value=env.get(v.name());
                if (value==null) throw new IllegalArgumentException("variable inconnue: " + v.name());
                yield value;
            }
            case Binary binary -> {
                double left=eval(binary.left(),env);
                double right=eval(binary.right(),env);
                yield switch(binary.op()){
                  case '+' -> left+right;
                  case '-' ->left -right;
                  case '*' -> left *right;
                  case '/' ->{
                      if (right == 0) throw new ArithmeticException("division par zéro");
                      yield left / right;
                  }
                    default -> throw new IllegalStateException("Unexpected value: " + binary.op());
                };
            }
            default -> throw new IllegalStateException("Unexpected value: " + expr);
        };
    }


}
