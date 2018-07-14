package name.wendelaar.jsnow;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        try {
            byte[] data = Files.readAllBytes(Paths.get("test.snow"));

            String dataFromFile = new String(data);
            System.out.println("Ori:\n " + dataFromFile);

            JSnowObject object = JSnow.parse(dataFromFile);

            String source = JSnow.unparse(object);

            System.out.println(source);

            PrintWriter writer = new PrintWriter("test2.snow");
            writer.print(source);
            writer.flush();
            writer.close();

            System.out.println(object.getArray("simpleArray").getSection(0).get("name"));
            System.out.println(object.getArray("simpleArray").getSection(1).get("name"));
            System.out.println(object.get("simpleKey"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
