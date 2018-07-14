package name.wendelaar.jsnow;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class JSnowObject extends JSnowGroup {

    private Map<String, Object> nodes;

    public JSnowObject() {
        this.nodes = new LinkedHashMap<>();
    }

    public boolean containsKey(String key) {
        return nodes.containsKey(key);
    }

    public Object get(String key) {
        return nodes.get(key);
    }

    public String getString(String key) {
        Object value = get(key);
        return value instanceof String ? (String) value : value.toString();
    }

    public Boolean getBoolean(String key) {
        Object value = get(key);
        return value instanceof Boolean ? (Boolean) value : false;
    }

    public JSnowObject getSection(String key) {
        Object value = get(key);
        return value instanceof JSnowObject ? (JSnowObject) value : null;
    }

    public JSnowArray getArray(String key) {
        Object value = get(key);
        return value instanceof JSnowArray ? (JSnowArray) value : null;
    }

    public void set(String key, Object value) {
        checkValue(value);

        nodes.put(key, value);
    }

    public void remove(String key) {
        if (!nodes.containsKey(key)) {
            throw new IllegalArgumentException("Unknown key");
        }

        nodes.remove(key);
    }

    @Override
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    @Override
    public void clear() {
        nodes.clear();
    }

    @Override
    public int size() {
        return nodes.size();
    }

    public Set<Map.Entry<String, Object>> getChildren() {
        return nodes.entrySet();
    }
}
