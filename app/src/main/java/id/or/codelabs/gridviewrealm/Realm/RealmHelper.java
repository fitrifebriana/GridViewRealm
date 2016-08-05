package id.or.codelabs.gridviewrealm.Realm;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by FitriFebriana on 8/3/2016.
 */
public class RealmHelper {

    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    public void saveData(final Name name){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Name n = realm.copyToRealm(name);
            }
        });
    }

    public ArrayList<String> retrieveData(){
        ArrayList<String> usersNames = new ArrayList<>();
        RealmResults<Name> names = realm.where(Name.class).findAll();
        for (Name name : names) {
            usersNames.add(name.getName());
        }
        return usersNames;
    }
}
