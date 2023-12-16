package fr.univ_lyon1.info.m1.elizagpt.model.Dao;


import fr.univ_lyon1.info.m1.elizagpt.model.Pronouns;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import java.io.FileReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

/**
 * The type Verb dao.
 */
public class VerbDao extends AbstractMapDao<Verb> {

    private final String csvFile = "./src/main/java/fr/univ_lyon1/info/m1/elizagpt/"
            + "model/Dao/french-verb-conjugation.csv";

    private static List<String> headers = null;

    private final Pronouns pronouns;

    /**
     * Instantiates a new Verb dao.
     *
     * @param newPronouns the new pronouns
     */
    public VerbDao(final Pronouns newPronouns) {
        pronouns = newPronouns;
        try (Reader reader = new FileReader(csvFile);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            headers = csvParser.getHeaderNames();
            for (CSVRecord record : csvParser) {  //parcours des lignes
                Verb verb = new Verb(headers);
                for (String key : verb.getKey()) {
                    verb.replace(key, record.get(key));
                }
                super.add(verb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets en tetes.
     *
     * @return the en tetes
     */
    public List<String> getEnTetes() {
        return headers;
    }

    @Override
    public String add(final Verb element) throws NameAlreadyBoundException {
        Serializable serializable = super.add(element);
        try (Writer writer = new FileWriter(csvFile, true);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
             csvPrinter.printRecord(element.getLigne());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (String) serializable;
    }

    private void globalUpdate() {
        String csvFileTemp = csvFile + ".tmp";
        try (Writer writer = new FileWriter(csvFileTemp, true);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(headers);
            for (Verb verb : this.getValues()) {
                 csvPrinter.printRecord(verb.getLigne());
             }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Files.move(Paths.get(csvFileTemp),
                    Paths.get(csvFile), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void delete(final Verb element) throws NameNotFoundException {
        super.delete(element);
        globalUpdate();
    }

    @Override
    public void deleteById(final Serializable id)
            throws NameNotFoundException, InvalidNameException {
        super.deleteById(id);
        globalUpdate();
    }

    @Override
    public void update(final Serializable id, final Verb element) throws InvalidNameException {
        super.update(id, element);
        globalUpdate();
    }

    /**
     * Conjugate verb string.
     *
     * @param text the text
     * @return the string
     */
    public String conjugateVerb(final String text) {
        String pronouns1 = pronouns.getPronounHeader(text);
        if (pronouns1 == null) {
            return text;
        }
        String[] tenseVerb = new String[0];
        Serializable finalKey = null;
        outerLoop:
        for (Verb verb : this.getValues()) {
            for (Map.Entry<String, String> entry
                    : verb.getVerbMap()) {
                if (entry.getKey().contains(pronouns1)) {
                    String[] splited = text.split("\\b");
                    for (String testVerb : splited) {
                        if (testVerb.equals(entry.getValue()) && !entry.getValue().isEmpty()) {
                            tenseVerb = entry.getKey().split("\\|");
                            finalKey = getKeyForElement(verb);
                            break outerLoop;
                        }
                    }
                }
            }
        }

        try {
            String startHeader = tenseVerb[0] + "|" + tenseVerb[1] + "|" + tenseVerb[2];
            String finalHeader = tenseVerb[0] + "|" + tenseVerb[1] + "|"
                    + pronouns.getOppositeHeader(pronouns1);
            Verb verb = this.findOne(finalKey);
            return verb.changePerson(startHeader, finalHeader, text);
        } catch (NameNotFoundException e) {
            return text;
        } catch (InvalidNameException e) {
            return text;
        } catch (IndexOutOfBoundsException e) {
            return text;
        }

    }

    @Override
    protected Serializable getKeyForElement(final Verb element) {
        return element.getId();
    }
}
