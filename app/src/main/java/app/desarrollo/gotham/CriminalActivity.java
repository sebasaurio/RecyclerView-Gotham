package app.desarrollo.gotham;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import app.desarrollo.gotham.Entidades.Criminales;

public class CriminalActivity extends AppCompatActivity {

    TextView txtNombreCriminal,txtDireccionCriminal;
    ImageView imvCriminal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criminal);

        txtNombreCriminal = (TextView) findViewById(R.id.txtNombreCriminal);
        txtDireccionCriminal = (TextView) findViewById(R.id.txtDireccionCriminal);
        imvCriminal = (ImageView) findViewById(R.id.imvCriminal);

        Criminales criminal = (Criminales)getIntent().getSerializableExtra("Criminal");
        txtNombreCriminal.setText(criminal.getNombre());
        txtDireccionCriminal.setText(criminal.getDireccion());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = null;
        try {
            url = new URL(criminal.getRuta_imagen());
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imvCriminal.setImageBitmap(image);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
