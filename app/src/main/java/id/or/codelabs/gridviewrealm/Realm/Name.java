package id.or.codelabs.gridviewrealm.Realm;

import io.realm.RealmObject;

/**
 * Created by FitriFebriana on 8/3/2016.
 */
public class Name extends RealmObject {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
