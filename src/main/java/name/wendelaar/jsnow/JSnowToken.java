package name.wendelaar.jsnow;

public class JSnowToken {
    private JSnowTokenType type;
    private Object literal;

    public JSnowToken(JSnowTokenType type, Object literal) {
        this.type = type;
        this.literal = literal;
    }

    public JSnowTokenType getType() {
        return type;
    }

    public Object getLiteral() {
        return literal;
    }
}
