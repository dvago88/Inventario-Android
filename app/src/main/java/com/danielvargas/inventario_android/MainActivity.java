package com.danielvargas.inventario_android;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.danielvargas.inventario_android.adapters.ProductosAdapter;
import com.danielvargas.inventario_android.model.Productos;
import com.danielvargas.inventario_android.service.AlertDialogFragment;
import com.danielvargas.inventario_android.service.ProductosActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//TODO: Agregar mensaje que no hay data si no se puede conectar
public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String LISTA_PRODUCTOS = "LISTA_PRODUCTOS";
    private Productos[] mProductos;


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
    }

    @OnClick(R.id.startButton)
    public void lista(View view) {
        Intent intent = new Intent(this, ProductosActivity.class);
        intent.putExtra(LISTA_PRODUCTOS, mProductos);
        startActivity(intent);
    }
}
