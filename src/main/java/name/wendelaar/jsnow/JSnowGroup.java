package name.wendelaar.jsnow;

public abstract class JSnowGroup {

    protected void checkValue(Object value) {
        if (value == null || value instanceof Number ||
                value instanceof String || value instanceof Boolean ||
                value instanceof JSnowGroup) {
            return;
        }
        throw new IllegalArgumentException(value.getClass().getName() + " not allowed here");
    }

    public abstract boolean isEmpty();

    public abstract void clear();

    public abstract int size();
}
