package name.wendelaar.jsnow;

public class JSnow {

    public static JSnowObject parse(String source) throws JSnowException {
        JSnowLexer lexer = new JSnowLexer(source);

        JSnowTokenCollection collection = new JSnowTokenCollection(lexer.scanTokens());
        JSnowParser parser = new JSnowParser(collection);

        return parser.parse();
    }

    public static String unparse(JSnowObject object) {
        JSnowUnParser unParser = new JSnowUnParser(object);
        return unParser.unparse();
    }
}
