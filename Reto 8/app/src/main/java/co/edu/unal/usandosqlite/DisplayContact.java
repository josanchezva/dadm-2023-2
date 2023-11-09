package co.edu.unal.usandosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DisplayContact extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;
    private ArrayList<String> categories = new ArrayList<String>() {
        {
            add("Consultoria");
            add("Desarrollo a la medida");
            add("Fabrica de software");
        }
    };

    TextView name ;
    TextView url;
    TextView phone;
    TextView email;
    TextView service;
    Spinner classification;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        name = (TextView) findViewById(R.id.editTextName);
        url = (TextView) findViewById(R.id.editTextURL);
        phone = (TextView) findViewById(R.id.editTextPhone);
        email = (TextView) findViewById(R.id.editTextEmail);
        service = (TextView) findViewById(R.id.editTextService);
        classification = (Spinner) findViewById(R.id.spinnerClassification);

        // Spinner click listener
        classification.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        classification.setAdapter(dataAdapter);

        //clasification = (TextView) findViewById(R.id.editTextClassification);

        mydb = new DBHelper(this);
        mydb.insertContact("Tech Solutions Inc.", "http://www.techsolutions.com", "+1-555-123-4567", "info@techsolutions.com", "Consultoria", "Tecnología");
        mydb.insertContact("Innovate Software", "http://www.innovatesoftware.com", "+1-555-987-6543", "contact@innovatesoftware.com", "Desarrollo a la medida", "Tecnología");
        mydb.insertContact("CodeCrafters", "http://www.codecrafters.io", "+1-555-555-5555", "info@codecrafters.io", "Fabrica de software", "Tecnología");
        mydb.insertContact("Data Insight Group", "http://www.datainsightgroup.com", "+1-555-789-0123", "contact@datainsightgroup.com", "Consultoria", "Analítica");
        mydb.insertContact("SoftWorks Corp.", "http://www.softworkscorp.com", "+1-555-345-6789", "info@softworkscorp.com", "Desarrollo a la medida", "Tecnología");
        mydb.insertContact("CodeFusion Solutions", "http://www.codefusionsolutions.net", "+1-555-222-3333", "contact@codefusionsolutions.net", "Fabrica de software", "Tecnología");
        mydb.insertContact("DataMasters Inc.", "http://www.datamastersinc.com", "+1-555-456-7890", "info@datamastersinc.com", "Consultoria", "Analítica");
        mydb.insertContact("WebCrafters", "http://www.webcrafters.io", "+1-555-654-3210", "contact@webcrafters.io", "Desarrollo a la medida", "Tecnología");
        mydb.insertContact("CodeGenius", "http://www.codegenius.com", "+1-555-789-1234", "info@codegenius.com", "Fabrica de software", "Tecnología");
        mydb.insertContact("AnalyticsTech", "http://www.analyticstech.net", "+1-555-987-6543", "contact@analyticstech.net", "Consultoria", "Analítica");
        mydb.insertContact("CustomSoft", "http://www.customsoft.io", "+1-555-222-4444", "info@customsoft.io", "Desarrollo a la medida", "Tecnología");
        mydb.insertContact("SoftwareCrafters", "http://www.softwarecrafters.com", "+1-555-333-5555", "contact@softwarecrafters.com", "Fabrica de software", "Tecnología");
        mydb.insertContact("DataInsights", "http://www.datainsightsolutions.com", "+1-555-555-1234", "info@datainsightsolutions.com", "Consultoria", "Analítica");
        mydb.insertContact("Tech Innovators", "http://www.techinnovators.io", "+1-555-444-7777", "contact@techinnovators.io", "Desarrollo a la medida", "Tecnología");
        mydb.insertContact("CodeWizards", "http://www.codewizards.com", "+1-555-123-9876", "info@codewizards.com", "Fabrica de software", "Tecnología");
        mydb.insertContact("AnalyticsPro", "http://www.analyticspro.net", "+1-555-678-9012", "contact@analyticspro.net", "Consultoria", "Analítica");
        mydb.insertContact("CustomSolutions", "http://www.customsolutionscorp.com", "+1-555-789-5678", "info@customsolutionscorp.com", "Desarrollo a la medida", "Tecnología");
        mydb.insertContact("CodeMasters", "http://www.codemasters.io", "+1-555-456-7891", "contact@codemasters.io", "Fabrica de software", "Tecnología");
        mydb.insertContact("DataTech Solutions", "http://www.datatechsolutions.com", "+1-555-222-6666", "info@datatechsolutions.com", "Consultoria", "Analítica");
        mydb.insertContact("Innovative Software", "http://www.innovativesoftware.net", "+1-555-333-8888", "contact@innovativesoftware.net", "Desarrollo a la medida", "Tecnología");

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                String ur = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_URL));
                String phon = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
                String emai = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
                String serv = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_SERVICE));
                String clasif = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CLASIFICATION));

                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.buttonSave);
                b.setVisibility(View.INVISIBLE);

                name.setText((CharSequence)nam);
                name.setFocusable(false);
                name.setClickable(false);

                url.setText((CharSequence)ur);
                url.setFocusable(false);
                url.setClickable(false);

                phone.setText((CharSequence)phon);
                phone.setFocusable(false);
                phone.setClickable(false);

                email.setText((CharSequence)emai);
                email.setFocusable(false);
                email.setClickable(false);

                service.setText((CharSequence)serv);
                service.setFocusable(false);
                service.setClickable(false);

                classification.setSelection(categories.indexOf(clasif));
                classification.setFocusable(false);
                classification.setEnabled(false);
                //classification.setClickable(false);
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_contact, menu);
            } else{
                getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.Edit_Contact:
                Button b = (Button)findViewById(R.id.buttonSave);
                b.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                url.setEnabled(true);
                url.setFocusableInTouchMode(true);
                url.setClickable(true);

                phone.setEnabled(true);
                phone.setFocusableInTouchMode(true);
                phone.setClickable(true);

                email.setEnabled(true);
                email.setFocusableInTouchMode(true);
                email.setClickable(true);

                service.setEnabled(true);
                service.setFocusableInTouchMode(true);
                service.setClickable(true);

                classification.setEnabled(true);
                classification.setFocusableInTouchMode(true);
                classification.setClickable(true);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteContact(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Eliminado Exitosamente",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("¿Está seguro?");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                if(
                        mydb.updateContact(
                                id_To_Update,name.getText().toString(),
                                url.getText().toString(), phone.getText().toString(),
                                email.getText().toString(), service.getText().toString(),
                                classification.getSelectedItem().toString()
                            //"clasification.getText().toString()"
                        )
                ){
                    Toast.makeText(getApplicationContext(), "Actualizado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "NO ACTUALIZADO", Toast.LENGTH_SHORT).show();
                }
            } else{
                if(
                        mydb.insertContact(
                                name.getText().toString(),
                                url.getText().toString(), phone.getText().toString(),
                                email.getText().toString(), service.getText().toString(),
                                classification.getSelectedItem().toString()
                                //"clasification.getText().toString()"
                        )
                ){
                    Toast.makeText(getApplicationContext(), "REGISTRADO",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "NO REGISTRADO",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
    }
}