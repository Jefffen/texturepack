import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.stream.Stream;

public class Main {

    /**
     * Tu umieszczasz scieżkę, gdzie pliki mają trafić po uruchomieniu
     */
    private static String destinationPath = "C:\\PATH\\TO\\FILE";



    private static final boolean debug = false;








    private static final Set<String> allowed = Set.of("pack.mcmeta", "pack.png", "copyright.txt");

    public static void main(String[] args) {
        String sourcePath = System.getProperty("project.dir");
        destinationPath = destinationPath + "\\SkyMMO";


        try {
            Path source = Paths.get(sourcePath);
            Path destination = Paths.get(destinationPath);


            try {
                Files.createDirectories(destination);
            }catch (IOException e){
                if (debug) {
                    e.printStackTrace();
                } else System.out.println("Błąd ścieżki!");
            }




            try (Stream<Path> filesStream = Files.walk(source)) {
                filesStream.forEach(sourceFile -> {
                    if (allowed.contains(sourceFile.getFileName().toString()) || sourceFile.toString().contains("assets")) {
                        System.out.println(sourceFile.getFileName());
                        try {
                            Path destinationFile = destination.resolve(source.relativize(sourceFile));
                            Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            if (debug) {
                                e.printStackTrace();
                            } else System.out.println("Błąd");
                        }
                    }
                });
            }
            System.out.println("Kopiowanie zakończone!");

        } catch (IOException e){
            if (debug) {
                e.printStackTrace();
            } else System.out.println("Błąd");
        }
    }
}