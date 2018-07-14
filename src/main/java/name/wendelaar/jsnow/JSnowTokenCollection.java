package name.wendelaar.jsnow;

import java.util.List;

public class JSnowTokenCollection {

    private List<JSnowToken> tokens;
    private int current;

    public JSnowTokenCollection(List<JSnowToken> tokens) {
        this.tokens = tokens;
    }

    public boolean isAtEnd() {
        return peek().getType() == JSnowTokenType.EOF;
    }

    public JSnowToken peek() {
        return tokens.get(current);
    }

    public JSnowToken peekPrevious() {
        return tokens.get(current-1);
    }

    public JSnowToken advance() {
        return tokens.get(current++);
    }

    public void silentAdvance() throws JSnowException {
        if (isAtEnd()) throw new JSnowException("String input malformed");
        current++;
    }

    public boolean match(JSnowTokenType... types) throws JSnowException {
        if (isAtEnd()) {
            return false;
        }

        JSnowToken token = peek();
        for (JSnowTokenType type : types) {
            if (token.getType() == type) {
                silentAdvance();
                return true;
            }
        }
        return false;
    }
}
