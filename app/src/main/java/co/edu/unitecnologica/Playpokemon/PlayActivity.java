package co.edu.unitecnologica.pokerpg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    private ImageLoader imageLoader;
    NetworkImageView img1, img2;
    TextView tx1,tx2;
    String urlimg1="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/", urlimg2="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
    public final String url = "http://pokeapi.co/api/v2/pokemon/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RequestQueue queue = Volley.newRequestQueue(this);
        int pokemon1, pokemon2;
        Random rand = new Random();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        img1 = (NetworkImageView) findViewById(R.id.pk1);
        img2 = (NetworkImageView) findViewById(R.id.pk2);
        tx1 = (TextView) findViewById(R.id.textView1);
        tx2 = (TextView) findViewById(R.id.textView2);
        pokemon1 = rand.nextInt(721) + 1;
        pokemon2 = rand.nextInt(721) + 1;
        while (pokemon1 == pokemon2)
            pokemon2 = rand.nextInt(721) + 1;
        Log.d("najada", url + String.valueOf(pokemon1)+"/");
        JsonObjectRequest getRequest1 = new JsonObjectRequest(Request.Method.GET, url + String.valueOf(pokemon1)+"/",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            loadImage(img1,response.getJSONObject("sprites").getString("back_default"));
                            tx1.setText(response.getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "lala");
                    }
                });
        JsonObjectRequest getRequest2 = new JsonObjectRequest(Request.Method.GET, url + String.valueOf(pokemon2)+"/", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            loadImage(img2,response.getJSONObject("sprites").getString("front_default"));
                            tx2.setText(response.getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "lala");
                    }
                });
        queue.add(getRequest1);
        queue.add(getRequest2);
    }

    private void loadImage(NetworkImageView imageView, String urlImg){
        imageLoader = co.edu.unitecnologica.pokerpg.HttpRequest.getInstance(this.getApplicationContext())
                .getImageLoader();
        imageLoader.get(urlImg, ImageLoader.getImageListener(imageView,
                R.drawable.white, android.R.drawable
                        .ic_dialog_alert));
        imageView.setImageUrl(urlImg, imageLoader);
    }

}