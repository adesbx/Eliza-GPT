package fr.univ_lyon1.info.m1.elizagpt.model;

import fr.univ_lyon1.info.m1.elizagpt.model.Dao.Verb;
import fr.univ_lyon1.info.m1.elizagpt.model.Dao.VerbDao;
import org.junit.jupiter.api.Test;

import javax.naming.InvalidNameException;
import javax.naming.NameNotFoundException;
import java.io.IOException;

public class DaoTest {
    private VerbDao verbDao = new VerbDao();

    @Test
    void getEnTete() {
        System.out.println("En-têtes du CSV : " + verbDao.getEnTetes());
    }

    @Test
    void getVerb() {
        try {
            Verb verb = verbDao.findOne("nager");
            System.out.println(verb.getLigne());
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvalidNameException e) {
            throw new RuntimeException(e);
        }
    }




}
