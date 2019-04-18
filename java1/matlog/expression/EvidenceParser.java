package matlog.expression;

import matlog.expression.parser.Expression;
import matlog.expression.proof.Exception.ProofException;
import matlog.expression.proof.Proof;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Kochetkov Nikita M3234
 * Date: 17.03.2019
 */
public class EvidenceParser {
    public static void main(String[] args) {
        new EvidenceParser().run();
    }

    void run() {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("input.txt"))) {
            String statement;
            Proof proof = new Proof();
            try {
                while ((statement = bufferedReader.readLine()) != null) {
                    proof.addInsecure(statement);
                }
            } catch (ProofException proofException) {
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
