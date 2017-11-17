package tn.agil.miniprojet.agil;

import android.app.Activity;
import android.telephony.SmsManager;
import android.widget.EditText;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SANA on 16/11/2017.
 */

public class envoieSMS extends Activity {


    RequestQueue requestQueue;

    private EditText Tel;
    String tel;
    private Button btnajouter;
    private String message;
    String showUrl="http://192.168.1.5:81/WebService1/ajouterSMS.php";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tel = (EditText) findViewById(R.id.editT);
        Bundle extras = getIntent().getExtras();
        tel = extras.getString("Tel");
        Tel.setText(tel);
        message="Cher(e) client(e),vous etes participer au tirage au sort . AGIL vous remercie pour votre fidelite .  ";
        btnajouter=(Button) findViewById(R.id.btnAjout);
        btnajouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMS();
                insert ();
            }
        });
    }

    protected void sendSMS() {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(tel,null,message,null,null);
    }
    public void insert ( ){
        StringRequest strReq = new StringRequest(Request.Method.POST,
                showUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                Toast.makeText(envoieSMS.this,"Message Envouyé",Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            public void onErrorResponse(VolleyError error) {
                Toast.makeText(envoieSMS.this,"Message Erreur",Toast.LENGTH_SHORT).show();


            }
        }) {


            //
            protected Map<String, String> getParams() {
                Map<String, String> parametres = new HashMap<String, String>();
                parametres.put("Tel",Tel.getText().toString());
                parametres.put("message",message);

                return parametres;
            }

        };


        requestQueue.add(strReq);
    }

}