package name.wendelaar.jsnow;

import java.util.ArrayList;
import java.util.List;

import static name.wendelaar.jsnow.JSnowTokenType.*;

public class JSnowLexer {

    private String jsonString;
    private int start = 0;
    private int current = 0;
    private List<JSnowToken> tokens;

    public JSnowLexer(String jsonString) {
        this.jsonString = jsonString;
        this.tokens = new ArrayList<>();
    }

    public List<JSnowToken> scanTokens() throws JSnowException {
        while (!isAtEnd()) {
            start = current;

            scanToken(advance());
        }

        addToken(EOF);
        return tokens;
    }

    public List<JSnowToken> getTokens() {
        return tokens;
    }

    private void scanToken(char c) throws JSnowException {
        switch (c) {
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '[': addToken(LEFT_BRACKET); break;
            case ']': addToken(RIGHT_BRACKET); break;
            case ',': addToken(COMMA); break;
            case ':': addToken(COLON); break;
            case ' ': case '\r': case '\t': case '\n': break;
            case '"':
                while (!isAtEnd() && peek() != '"') advance();

                if (isAtEnd()); //add error;

                addToken(STRING, jsonString.substring(start + 1, current));
                advance();
                break;
            default:
                if (isDigit(c)) {
                    number();
                    return;
                } else if (isAlpha(c)) {
                    while (!isAtEnd() && isAlpha(peek())) advance();

                    String literal = jsonString.substring(start, current);

                    switch (literal) {
                        case "true": addToken(TRUE, true); return;
                        case "false": addToken(FALSE, false); return;
                        case "null": addToken(NULL, null); return;
                    }
                    throw new JSnowException("Unexpected literal '" + literal + "'");
                }
                throw new JSnowException("Unexpected character '" + c + "'");
        }
    }

    private void number() {
        while (!isAtEnd() && isDigit(peek())) advance();

        if (!isAtEnd() && peek() == '.') {
            advance();
            while (!isAtEnd() && isDigit(peek())) advance();
        }

        addToken(NUMBER, Double.parseDouble(jsonString.substring(start, current)));
    }

    private void addToken(JSnowTokenType type) {
        addToken(type, null);
    }

    private void addToken(JSnowTokenType type, Object literal) {
        tokens.add(new JSnowToken(type, literal));
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isAtEnd() {
        return current >= jsonString.length();
    }

    private char peek() {
        return jsonString.charAt(current);
    }

    private char advance() {
        if (isAtEnd()) return '\0';
        return jsonString.charAt(current++);
    }
}
