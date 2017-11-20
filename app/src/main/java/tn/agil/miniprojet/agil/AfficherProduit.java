package tn.agil.miniprojet.agil;
/**
 * Created by Linda on 12/11/2017.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class AfficherProduit extends Activity {
    String showUrl="http://192.168.1.100:81/WebService1/listepromoprod.php";
    private ListView listView=null;
    private ArrayList<String> list = new ArrayList<String>();
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_produit);
        listView = (ListView) findViewById(R.id.listView1);
        requestQueue = Volley.newRequestQueue(this);
        recup();
        ImageButton btn = (ImageButton) findViewById(R.id.fab);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1= new Intent(getApplicationContext(),AjoutClient.class);
                startActivity(i1);
            }
        });
    }

    public void recup(){




        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,showUrl,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray produits = response.getJSONArray("produits");
                    int i=0;

                    for (i = 0; i < produits.length(); i++) {
                        JSONObject produit = produits.getJSONObject(i);
                        //add list

                        String nom = produit.getString("nomproduit");
                        String quantite = produit.getString("quantite");
                        String prix= produit.getString("prixfinal");
                        String dateD= produit.getString("datedebut");
                        String dateF= produit.getString("datefin");
                        // String marque = produit
                        String ligne = nom+"\n "+quantite+"  \n"+prix +"DT"
                                +"\n "+dateD+" \n "+dateF;
                        list.add(ligne);

                    }

                    String[] items = new String[list.size()];
                    items = list.toArray(items);

                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(AfficherProduit.this,
                            R.layout.list_item, R.id.txtitem,items);

                    listView.setAdapter(adapter);

                    // Perform action when an item is clicked

                    listView.setOnItemClickListener(new
                                                            AdapterView.OnItemClickListener() {

                                                                @Override

                                                                public void onItemClick(AdapterView<?> parent, View view, int
                                                                        position, long id) {

                                                                    ViewGroup vg=(ViewGroup)view;
                                                                    String item = listView.getItemAtPosition(position).toString();



                                                                }

                                                            });
                    // Toast.makeText(MainActivity.this, list.toString(), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    Toast.makeText(AfficherProduit.this, "Ereur DB", Toast.LENGTH_SHORT).show();

                }

            }
        },

                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AfficherProduit.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();

                    }



                }){


        };
        requestQueue.add(jsonObjectRequest);

    }

}
