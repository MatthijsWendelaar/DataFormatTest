package name.wendelaar.jsnow;

public class JSnowToken {
    JSnowTokenType type;
    Object literal;

    public JSnowToken(JSnowTokenType type, Object literal) {
        this.type = type;
        this.literal = literal;
    }
}
