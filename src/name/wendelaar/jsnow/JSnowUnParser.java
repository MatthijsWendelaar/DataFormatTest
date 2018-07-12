package name.wendelaar.jsnow;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JSnowUnParser {

    private StringBuilder builder;
    private JSnowObject object;
    private int deep = 0;
    private boolean hasKey;


    public JSnowUnParser(JSnowObject object) {
        this.object = object;
        this.builder = new StringBuilder();
    }

    public String unparse() {
        handleObject(object);
        return builder.toString();
    }

    private void handleObject(JSnowObject object) {
        if (hasKey) {
            hasKey = false;
        } else {
            appendWhiteSpace();
        }

        builder.append("{\n");

        if (object.isEmpty()) {
            appendChar('}',true);
            return;
        }
        deep++;

        Iterator<Map.Entry<String, Object>> iterator = object.getChildren().iterator();

        Map.Entry<String, Object> firstValue = iterator.next();

        handleKey(firstValue.getKey());
        handleValue(firstValue.getValue());


        while (iterator.hasNext()) {
            builder.append(",\n");
            Map.Entry<String, Object> value = iterator.next();
            handleKey(value.getKey());
            handleValue(value.getValue());
        }
        deep--;
        appendChar('}',false);
    }

    private void handleArray(JSnowArray array) {
        if (hasKey) {
            hasKey = false;
        } else {
            appendWhiteSpace();
        }
        builder.append("[\n");

        if (array.isEmpty()) {
            appendChar(']', true);
            return;
        }
        deep++;

        List<Object> children = array.getChildren();
        handleValue(children.get(0));

        for (int i = 1; i < array.size(); ++i) {
            builder.append(",\n");
            handleValue(children.get(i));
        }
        deep--;
        appendChar(']', false);
    }

    private void handleValue(Object value) {

        if (value instanceof String) {
            stringify((String) value);
            hasKey = false;
        } else if (value instanceof Double) {
            double flooredValue = Math.floor((Double) value);
            if (flooredValue == (Double) value) {
                value = ((Double) value).intValue();
            }
            builder.append(value);
            hasKey = false;
        } else if (value instanceof JSnowObject) {
            handleObject((JSnowObject) value);
        } else if (value instanceof JSnowArray) {
            handleArray((JSnowArray) value);
        } else {
            builder.append(value);
            hasKey = false;
        }
    }

    private void handleKey(String key) {
        hasKey = true;
        appendWhiteSpace();
        stringify(key);
        builder.append(": ");
    }

    private void appendChar(char c,boolean empty) {
        if (!empty) {
            builder.append('\n');
        }
        appendWhiteSpace();
        builder.append(c);
    }

    private void appendWhiteSpace() {
        for (int i = 0; i < deep; i++) {
            builder.append("    ");
        }
    }

    private void stringify(String text) {
        builder.append('\"').append(text).append('\"');
    }
}
