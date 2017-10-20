package app.desarrollo.gotham;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.desarrollo.gotham.Adapters.CriminalesAdapter;
import app.desarrollo.gotham.Entidades.Criminales;

public class MainActivity extends AppCompatActivity {

    private List<Criminales> listaCriminales;
    private RecyclerView recyclerCriminales;
    private CriminalesAdapter criminalesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Criminales> listaJsonCriminales = new ArrayList<>();
        try {
            InputStream input_json = getResources().openRawResource(R.raw.pregunta_3);

            int size = input_json.available();
            byte[] buffer = new byte[size];
            input_json.read(buffer);
            input_json.close();
            String myJson = new String(buffer, "UTF-8");

            JSONArray criminalesJson = new JSONArray(myJson);
            Log.i("json",criminalesJson.length()+"-");

            Criminales criminal;
            JSONObject objectCriminal;
            for(int i = 0; i<criminalesJson.length();i++){
                objectCriminal = criminalesJson.getJSONObject(i);
                criminal = new Criminales();
                criminal.setNombre(objectCriminal.getString("nombre"));
                criminal.setLatitud(objectCriminal.getDouble("latitud"));
                criminal.setLongitud(objectCriminal.getDouble("longitud"));
                criminal.setDireccion(obtenerDireccion(objectCriminal.getDouble("latitud"),objectCriminal.getDouble("longitud")));
                criminal.setRuta_imagen(objectCriminal.getString("imagen2"));
                listaJsonCriminales.add(criminal);
            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        listaCriminales = listaJsonCriminales;

        recyclerCriminales = (RecyclerView)findViewById(R.id.recyclerCriminales);
        criminalesAdapter = new CriminalesAdapter(listaCriminales, getApplicationContext());
        recyclerCriminales.setAdapter(criminalesAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerCriminales.setLayoutManager(mLayoutManager);
        recyclerCriminales.setItemAnimator(new DefaultItemAnimator());

    }

    public String obtenerDireccion(double latitud, double longitud){

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> direcciones = geocoder.getFromLocation(latitud, longitud, 1);
            if(direcciones != null){
                String direccion = direcciones.get(0).getAddressLine(0);
                String ciudad = direcciones.get(0).getLocality();
                String pais = direcciones.get(0).getCountryName();
                return direccion+" "+ciudad+" "+" "+pais;
            }else{
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
