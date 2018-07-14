package name.wendelaar.jsnow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JSnowArray extends JSnowGroup {

    private List<Object> objects;

    public JSnowArray() {
        objects = new ArrayList<>();
    }

    @Override
    public boolean isEmpty() {
        return objects.isEmpty();
    }

    public void add(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("value can not be null");
        }

        checkValue(value);
        objects.add(value);
    }

    public Object get(int index) {
        return objects.get(index);
    }

    public String getString(int index) {
        Object value = get(index);
        return value instanceof String ? (String) value : value.toString();
    }

    public JSnowObject getSection(int index) {
        Object value = get(index);
        return value instanceof JSnowObject ? (JSnowObject) value : null;
    }

    @Override
    public int size() {
        return objects.size();
    }

    public void remove(int index) {
        objects.remove(index);
    }

    @Override
    public void clear() {
        objects.clear();
    }

    public List<Object> getChildren() {
        return Collections.unmodifiableList(objects);
    }
}
