package tn.agil.miniprojet.agil;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


public class AfficherProduit extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] values = new String[]{"TANIX 4WD SAE 15W50",
                "TANIX DIESEL 700 SAE 10W40", "TANIX DIESEL 500 SAE 40",
                "ENI I-SINT 10W40"};

        MonAdaptateurDeListe adaptateur = new MonAdaptateurDeListe(this, values);

        setListAdapter(adaptateur);
    }
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(this, "Position : " + position, Toast.LENGTH_LONG).show();
    }
    }

