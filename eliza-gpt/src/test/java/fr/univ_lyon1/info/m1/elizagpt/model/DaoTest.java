//package fr.univ_lyon1.info.m1.elizagpt.model;
//
//import fr.univ_lyon1.info.m1.elizagpt.model.Dao.Verb;
//import fr.univ_lyon1.info.m1.elizagpt.model.Dao.VerbDao;
//import org.junit.jupiter.api.Test;
//
//import javax.naming.InvalidNameException;
//import javax.naming.NameAlreadyBoundException;
//import javax.naming.NameNotFoundException;
//import java.util.Map;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
//
///**
// * class for testing conjugaison thinks.
// */
//public class DaoTest {
//    private Pronouns pronouns = new Pronouns();
//    private VerbDao verbDao = new VerbDao(pronouns);
//
//    /**
//     * Print all headers form the .csv file.
//     */
//    @Test
//    void getEnTete() {
//        assert(verbDao.getEnTetes() != null);
//        System.out.println(verbDao.getEnTetes());
//    }
//
//    /**
//     * test the getter.
//     */
//    @Test
//    void getVerb() {
//        try {
//            Verb verb = verbDao.findOne("être_ahah");
//            assertThat(verb.getByHeader("infinitive"), is(verb.getId()));
//        } catch (NameNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (InvalidNameException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void setVerb() throws InvalidNameException, NameNotFoundException, NameAlreadyBoundException {
//        Verb newVerb = new Verb(verbDao.getEnTetes());
//        Verb getVerb = verbDao.findOne("être");
//        for(Map.Entry<String, String> conjugaison : getVerb.getVerbMap()) {
//            newVerb.replace(conjugaison.getKey(), conjugaison.getValue()+"_ahah");
//        }
//        verbDao.add(newVerb);
//    }
//
//    @Test
//    void deleteVerb() throws InvalidNameException, NameNotFoundException {
//        Verb newVerb = new Verb(verbDao.getEnTetes());
//        Verb getVerb = verbDao.findOne("être");
//        for(Map.Entry<String, String> conjugaison : getVerb.getVerbMap()) {
//            newVerb.replace(conjugaison.getKey(), conjugaison.getValue()+"_ahahah");
//        }
//        verbDao.delete(newVerb);
//    }
//
//    @Test
//    void updateVerb() throws InvalidNameException, NameNotFoundException {
//        Verb newVerb = new Verb(verbDao.getEnTetes());
//        Verb getVerb = verbDao.findOne("être");
//        for(Map.Entry<String, String> conjugaison : getVerb.getVerbMap()) {
//            newVerb.replace(conjugaison.getKey(), conjugaison.getValue()+"_ahahah");
//        }
//        verbDao.update(newVerb.getId(), newVerb);
//    }
//
//}
