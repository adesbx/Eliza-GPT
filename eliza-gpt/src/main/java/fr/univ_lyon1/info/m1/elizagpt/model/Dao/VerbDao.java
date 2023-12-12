package fr.univ_lyon1.info.m1.elizagpt.model.Dao;


import java.io.FileReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class VerbDao extends AbstractMapDao<Verb> {

    private final static String CSV_FILE = "/home/valou/Master1/Gestion_projet/mif-01-chat-ptdr/eliza-gpt/src/main/java/fr/univ_lyon1/info/m1/elizagpt/model/Dao/french-verb-conjugation.csv";


    public VerbDao() {
        try (Reader reader = new FileReader(CSV_FILE);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            List<String> enTetes = csvParser.getHeaderNames();
            System.out.println("En-têtes du CSV : " + enTetes);
            for (CSVRecord record : csvParser) {  //parcours des lignes
                Verb verb = new Verb(enTetes);
                for (String key : verb.verbMap.keySet()) {
                    verb.verbMap.replace(key, record.get(key));
                }
                super.add(verb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Serializable getKeyForElement(Verb element) {
        return element.getId();
    }
}