package name.wendelaar.jsnow;

import static name.wendelaar.jsnow.JSnowTokenType.*;

public class JSnowParser {

    private JSnowTokenCollection collection;

    public JSnowParser(JSnowTokenCollection collection) {
        this.collection = collection;
    }

    public JSnowObject parse() throws JSnowException {
        check(collection.match(LEFT_BRACE), "Expected '{' at start of object");

        JSnowObject object = parseObject();

        if (!collection.isAtEnd()) {
            throw new JSnowException("Unexpected data after closing '}' of root object");
        }

        return object;
    }

    private JSnowObject parseObject() throws JSnowException {

        JSnowObject object = new JSnowObject();

        while (!collection.isAtEnd()) {
            String key = parseKey(object);
            Object value = parseNode();

            object.set(key, value);

            if (!collection.match(COMMA)) {
                break;
            }
        }

        check(collection.match(RIGHT_BRACE), "Expected '}' at end of object");
        return object;
    }

    private String parseKey(JSnowObject parent) throws JSnowException {
        check(collection.match(STRING), "Node must start with string literal as key");

        String stringKey = (String) collection.peekPrevious().getLiteral();
        if (parent.containsKey(stringKey)) {
            throw new JSnowException("Duplicate keys are not allowed");
        }
        check(collection.match(COLON), "Expected ':' after key of Node");
        return stringKey;
    }

    private Object parseNode() throws JSnowException {
        JSnowToken value = collection.advance();
        Object node;
        switch (value.getType()) {
            case NULL: case TRUE: case FALSE: case NUMBER: case STRING:
                node = value.getLiteral();
                break;
            case LEFT_BRACE:
                node = parseObject();
                break;
            case LEFT_BRACKET:
                node = parseArray();
                break;
            default:
                throw new JSnowException("Unexpected value '" + value.getLiteral() + "' for node");
        }
        return node;
    }

    private JSnowArray parseArray() throws JSnowException {
        JSnowArray object = new JSnowArray();

        while (!collection.isAtEnd()) {
            Object value = parseNode();
            object.add(value);

            if (!collection.match(COMMA)) {
                break;
            }
        }

        check(collection.match(RIGHT_BRACKET), "Expected ']' at end of array");
        return object;
    }

    private void check(boolean value, String message) throws JSnowException {
        if (!value) throw new JSnowException(message);
    }
}
