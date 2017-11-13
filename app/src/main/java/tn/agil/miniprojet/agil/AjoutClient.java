package tn.agil.miniprojet.agil;

/**
 * Created by Linda on 25/10/2017.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AjoutClient extends AppCompatActivity  {

    private EditText cin;
    private EditText nom;
    private EditText prenom;
    private EditText tel;
    private Button btnajouter;
    private EditText qte;
    private Spinner station;
    private Spinner produit;
    private String[] arraySpinner;
    private String[] arraySpinner2;
    boolean exist=false;
    String idStat;
    String idProd;
    RequestQueue requestQueue;
    RequestQueue requestQueue2;
    RequestQueue requestQueue3;
    RequestQueue requestQueue4;
    ArrayList<String> listSation =new ArrayList<String>();
    ArrayList<String> listProm =new ArrayList<String>();
    ArrayList<String> listProd =new ArrayList<String>();


    String showUrl="http://192.168.137.1/test/ajoutClient.php";
    String recupProduitUrl="http://192.168.137.1/test/listeproduit.php";
    String recupStationUrl="http://192.168.137.1/test/listestation.php";
    String recupPromoUrl="http://192.168.137.1/test/listepromotion.php";
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_client);
        cin = (EditText) findViewById(R.id.editCin);
        nom = (EditText) findViewById(R.id.editN);
        prenom = (EditText) findViewById(R.id.editP);
        tel = (EditText) findViewById(R.id.editT);
        station = (Spinner) findViewById(R.id.stationList);
        produit = (Spinner) findViewById(R.id.prodList);


        qte = (EditText) findViewById(R.id.editQte);
        btnajouter = (Button) findViewById(R.id.btnAjout);
        btnajouter.setOnClickListener(new OnClickListener());

        //Setting the ArrayAdapter data on the Spinner

        requestQueue   = Volley.newRequestQueue(this);
        requestQueue2   = Volley.newRequestQueue(this);
        requestQueue3   = Volley.newRequestQueue(this);
        requestQueue4   = Volley.newRequestQueue(this);


        recupStation();



    }






    private class OnClickListener implements View.OnClickListener
    {
        public void onClick (View v)
        {
            String cinStr= cin.getText().toString();
            String nomStr= nom.getText().toString();
            String prenomStr= prenom.getText().toString();
            String telStr= tel.getText().toString();
            String prodStr= "aa";
            String qteStr= qte.getText().toString();


            if ((cinStr.isEmpty())||(nomStr.isEmpty())||(prenomStr.isEmpty())||(telStr.isEmpty())||
                    (prodStr.isEmpty())||(qteStr.isEmpty()))
            {
                Toast.makeText(AjoutClient.this,"Champs Vides !!",Toast.LENGTH_SHORT).show();
            }
            else{

               insert();

        }}

        public void insert ( ){
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    showUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(AjoutClient.this,"valide",Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AjoutClient.this,"invalide",Toast.LENGTH_SHORT).show();


                }
            }) {


                //
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parametres = new HashMap<String, String>();
                    parametres.put("cinclient",cin.getText().toString());
                    parametres.put("nomclient",nom.getText().toString());
                    parametres.put("prenomclient",prenom.getText().toString());
                    parametres.put("produitachete",idProd);
                    parametres.put("numtelclient",tel.getText().toString());
                    parametres.put("idvente",qte.getText().toString());
                    parametres.put("idstation",idStat);
                    return parametres;
                }

            };


            requestQueue.add(strReq);
        }
    }




    public void recupStation(){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,recupStationUrl,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {

                    final JSONArray stations = response.getJSONArray("station");

                    for (int i = 0; i < stations.length(); i++) {
                        JSONObject station = stations.getJSONObject(i);
                        String testid = station.getString("idstation");

                            listSation.add(testid);

                            exist=true;



                    }
                    if(exist==false){
                        throw new JSONException("erreur");
                    }

                   // Toast.makeText(AjoutClient.this, listSation.toString(), Toast.LENGTH_LONG).show();
                    arraySpinner=new String [listSation.size()];
                    arraySpinner=listSation.toArray(arraySpinner);

                    adapter = new ArrayAdapter<String>(AjoutClient.this,
                            android.R.layout.simple_spinner_item, arraySpinner);

                    station.setAdapter(adapter);
                    station.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent,
                                                   View view, int pos, long id) {

                            idStat=station.getItemAtPosition(pos).toString();
                            //Toast.makeText(AjoutClient.this, idStat, Toast.LENGTH_LONG).show();
                            listProd.clear();
                            recupPromo(idStat);



                        }



                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // your code here
                        }

                    });


                } catch (JSONException e) {

                    Toast.makeText(AjoutClient.this, "Verifier vos données", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AjoutClient.this,"Erreur de connection",Toast.LENGTH_SHORT).show();


            }
        }) {


        };


        requestQueue3.add(jsonObjectRequest);

    }

    public void recupProduit(){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,recupProduitUrl,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray produits = response.getJSONArray("produits");

                    for (int i = 0; i < produits.length(); i++) {
                        JSONObject produit = produits.getJSONObject(i);
                        String nomProduit = produit.getString("nomproduit");
                        String idProduit = produit.getString("idproduit");
                        Object o= idProduit;

                        if(listProm.contains(o)) {
                            exist = true;
                            listProd.add(nomProduit);

                        }


                    }
                    if(exist==false){
                        throw new JSONException("erreur de connection");
                    }


                    arraySpinner2=new String [listProd.size()];
                    arraySpinner2=listProd.toArray(arraySpinner2);

                    adapter2 = new ArrayAdapter<String>(AjoutClient.this,
                            android.R.layout.simple_spinner_item, arraySpinner2);

                    produit.setAdapter(adapter2);
                    produit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                          @Override
                                                          public void onItemSelected(AdapterView<?> parent,
                                                                                     View view, int pos, long id) {

                                                              idProd = produit.getItemAtPosition(pos).toString();


                                                          }
                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // your code here
                        }
                                                      });
                   // Toast.makeText(AjoutClient.this, listProd.toString(), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {

                    Toast.makeText(AjoutClient.this, "Verifier vos données", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AjoutClient.this,"invalide",Toast.LENGTH_SHORT).show();


            }
        }) {


        };


        requestQueue2.add(jsonObjectRequest);
    }


    public void recupPromo( String idS){
        listProm.clear();
        final String id = idS;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,recupPromoUrl,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray promotion = response.getJSONArray("promo");

                    for (int i = 0; i < promotion.length(); i++) {
                        JSONObject promo = promotion.getJSONObject(i);
                        String idProduit = promo.getString("idproduit");
                        String idStation = promo.getString("idstation");


                        if(idStation.equals(id)) {
                            exist = true;

                           listProm.add(idProduit);
                        }

                    }
                    if(exist==false){
                        throw new JSONException("erreur de connection");
                    }
                    Toast.makeText(AjoutClient.this,listProm.toString(), Toast.LENGTH_LONG).show();
                    listProd.clear();
                    recupProduit();

                } catch (JSONException e) {

                    Toast.makeText(AjoutClient.this, "Verifier vos données", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AjoutClient.this,"invalide",Toast.LENGTH_SHORT).show();


            }
        }) {


        };


        requestQueue4.add(jsonObjectRequest);
    }

}
