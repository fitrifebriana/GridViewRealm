package id.or.codelabs.gridviewrealm;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import id.or.codelabs.gridviewrealm.Realm.Name;
import id.or.codelabs.gridviewrealm.Realm.RealmHelper;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd;
    Realm realm;
    ArrayList<String> names = new ArrayList<>();
    ArrayAdapter adapter;
    EditText txtName;
    GridView gvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gvMain = (GridView) findViewById(R.id.gv_main);

        //Setup
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        realm = realm.getInstance(config);

        //Retrieve
        RealmHelper helper = new RealmHelper(realm);
        names = helper.retrieveData();

        //Bind
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
        gvMain.setAdapter(adapter);

        //Item click
        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), names.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertAddNewName();
            }
        });
    }

    private void alertAddNewName() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addNameForm = inflater.inflate(R.layout.input_dialog, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(addNameForm);
        builder.setTitle("Add New Name");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                txtName = (EditText) addNameForm.findViewById(R.id.txt_name);
                String name = txtName.getText().toString().trim();
                Name n = new Name();

                n.setName(name);

                RealmHelper helper = new RealmHelper(realm);
                helper.saveData(n);

                //Retrieve or refresh
                names = helper.retrieveData();
                adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, names);
                gvMain.setAdapter(adapter);

                dialog.cancel();
            }
        }).show();
    }
}
