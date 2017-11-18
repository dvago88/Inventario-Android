package com.danielvargas.inventario_android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private Productos[] mProductos;

    @BindView(R.id.id1)
    TextView id1;
    @BindView(R.id.id2)
    TextView id2;
    @BindView(R.id.id3)
    TextView id3;
    @BindView(R.id.id4)
    TextView id4;
    @BindView(R.id.nombre1)
    TextView nombre1;
    @BindView(R.id.nombre2)
    TextView nombre2;
    @BindView(R.id.nombre3)
    TextView nombre3;
    @BindView(R.id.nombre4)
    TextView nombre4;
    @BindView(R.id.precio1)
    TextView precio1;
    @BindView(R.id.precio2)
    TextView precio2;
    @BindView(R.id.precio3)
    TextView precio3;
    @BindView(R.id.precio4)
    TextView precio4;
    @BindView(R.id.vendidos1)
    TextView vendidos1;
    @BindView(R.id.vendidos2)
    TextView vendidos2;
    @BindView(R.id.vendidos3)
    TextView vendidos3;
    @BindView(R.id.vendidos4)
    TextView vendidos4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getData();
    }

    private void getData() {
        String dataUrl = "https://guarded-shelf-37041.herokuapp.com/api/productos";

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(dataUrl).build();
            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                    try {
                        String jsonData = response.body().string();//string nos da una representacion en string del response.body()
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mProductos = getJsonData(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException | JSONException e) {
                        Log.e(TAG, "Exeption cought: ", e);
                    }
                }
            });

        } else {
            Toast.makeText(this, R.string.network_unavilable_message, Toast.LENGTH_LONG).show();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();// este metodo tambien necesita que le demos un permiso en el android manifest
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment(); //declaramos el dialogo
        dialog.show(getFragmentManager(), "error_dialog"); //esto es para que se muestre
    }

    private Productos[] getJsonData(String jsonData) throws JSONException {
        JSONArray data = new JSONArray(jsonData);
        Productos[] productos = new Productos[data.length()];

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonProducto = data.getJSONObject(i);
            Productos producto = new Productos();

            producto.setId(jsonProducto.getInt("id"));
            producto.setNombre(jsonProducto.getString("nombre"));
            producto.setPrecio(jsonProducto.getDouble("precio"));
            producto.setPrecioEntrada(jsonProducto.getDouble("precioEntrada"));
            producto.setDescripcion(jsonProducto.getString("descripcion"));
            producto.setVendidos(jsonProducto.getInt("cantidadVendido"));

            productos[i] = producto;
        }

        return productos;
    }

    public void updateDisplay() {
        id1.setText(mProductos[0].getId() + "");
        id2.setText(mProductos[1].getId() + "");
        id3.setText(mProductos[2].getId() + "");
        id4.setText(mProductos[3].getId() + "");

        nombre1.setText(mProductos[0].getNombre());
        nombre2.setText(mProductos[1].getNombre());
        nombre3.setText(mProductos[2].getNombre());
        nombre4.setText(mProductos[3].getNombre());

        precio1.setText(mProductos[0].getPrecio() + "");
        precio2.setText(mProductos[1].getPrecio() + "");
        precio3.setText(mProductos[2].getPrecio() + "");
        precio4.setText(mProductos[3].getPrecio() + "");

        vendidos1.setText(mProductos[0].getVendidos() + "");
        vendidos2.setText(mProductos[1].getVendidos() + "");
        vendidos3.setText(mProductos[2].getVendidos() + "");
        vendidos4.setText(mProductos[3].getVendidos() + "");
    }

    @OnClick(R.id.actualizarButton)
    public void actualizar(View view){
        getData();
    }
}
