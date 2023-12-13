package fr.univ_lyon1.info.m1.elizagpt.model.Dao;


import java.io.FileReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.io.IOException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVPrinter;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

public class VerbDao extends AbstractMapDao<Verb> {

    private final static String CSV_FILE = "/home/valou/Master1/Gestion_projet/mif-01-chat-ptdr/eliza-gpt/src/main/java/fr/univ_lyon1/info/m1/elizagpt/model/Dao/french-verb-conjugation.csv";

    private static List<String> enTetes = null;

    public VerbDao() {
        try (Reader reader = new FileReader(CSV_FILE);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            enTetes = csvParser.getHeaderNames();
            for (CSVRecord record : csvParser) {  //parcours des lignes
                Verb verb = new Verb(enTetes);
                for (String key : verb.getKey()) {
                    verb.replace(key, record.get(key));
                }
                super.add(verb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getEnTetes() {
        return enTetes;
    }

    @Override
    public String add(Verb element) throws NameAlreadyBoundException {
        Serializable serializable = super.add(element);
        try (Writer writer = new FileWriter(CSV_FILE, true);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(element.getLigne());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (String) serializable;
    }

    private void globalUpdate() {
        String csvFileTemp = CSV_FILE+".tmp";
        try (Writer writer = new FileWriter(csvFileTemp, true);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (Verb verb : this.collection.values()) {
                csvPrinter.printRecord(verb.getLigne());
            }
            Files.move(Paths.get(csvFileTemp), Paths.get(CSV_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void delete(Verb element) throws NameNotFoundException {
        super.delete(element);
        globalUpdate();
    }

    @Override
    public void deleteById(Serializable id) throws NameNotFoundException, InvalidNameException {
        super.deleteById(id);
        globalUpdate();
    }

    @Override
    public void update(Serializable id, Verb element) throws InvalidNameException {
        super.update(id, element);
        globalUpdate();
    }

    public String conjugateVerb(String pronouns1, String pronouns2, String text) {
        String[] tenseVerb = new String[0];
        Serializable finalKey = null;
        outerLoop:
        for (Verb verb : this.collection.values()) {
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
            String startHeader = tenseVerb[0]+"|"+tenseVerb[1]+"|"+tenseVerb[2];
            String finalHeader = tenseVerb[0]+"|"+tenseVerb[1]+"|"+pronouns2;
            Verb verb = this.findOne(finalKey);
            return verb.changePerson(startHeader,finalHeader,text);
        } catch (NameNotFoundException e) {
            return text;
        } catch (InvalidNameException e) {
            return text;
        } catch (IndexOutOfBoundsException e) {
            return text;
        }

    }

    @Override
    protected Serializable getKeyForElement(Verb element) {
        return element.getId();
    }
}