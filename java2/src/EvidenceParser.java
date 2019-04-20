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
            try {
                while ((statement = bufferedReader.readLine()) != null) {
                    proof.addInsecure(statement);
                }
                //proof.checkLastEqualsStatement();
            } catch (ProofException proofException) {
                //System.out.println(proofException.getMessage());
                System.out.println("Proof is incorrect");
                return;
            }
            proof.createTreeUsage();
            proof.printProof();
        } catch (IOException e) {
            System.err.println("Error reading.");
        }
    }
}
