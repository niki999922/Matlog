import matlog.expression.proof.Exception.ProofException;
import matlog.expression.proof.Proof;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */
public class EvidenceParser {
    public static void main(String[] args) {
        new EvidenceParser().run();
    }

    void run() {
        //try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("input.txt"))) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            String statement;
            Proof proof = new Proof();
            while ((statement = bufferedReader.readLine()) != null) {
                proof.addSafe(statement);
            }
        } catch (IOException e) {
            System.err.println("Error reading.");
        }
    }
}
