package fr.univ_lyon1.info.m1.elizagpt.model.Dao;

import java.util.HashMap;
import java.util.Map;

public class Pronouns {
    private final Map<String, String[]> pronounsMap;
    public Pronouns() {
        pronounsMap = new HashMap<>(){{
            put("first person singular", new String[]{"je", "j'"});
            put("second person singular" , new String[]{"tu"});
            put("third person singular" , new String[]{"il", "elle", "on"});
            put("first person plural" , new String[]{"nous"});
            put("second person plural" , new String[]{"vous"});
            put("third person plural" , new String[]{"ils", "elles"});
        }};
    }

    public
}
