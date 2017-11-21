package com.danielvargas.inventario_android.service;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.danielvargas.inventario_android.MainActivity;
import com.danielvargas.inventario_android.R;
import com.danielvargas.inventario_android.adapters.ProductosAdapter;
import com.danielvargas.inventario_android.model.Productos;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductosActivity extends AppCompatActivity {

    private Productos[] mProductos;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productos_list);
        ButterKnife.bind(this);


        Intent intent = getIntent();
//        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.LISTA_PRODUCTOS);
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.LISTA_PRODUCTOS);
        mProductos = Arrays.copyOf(parcelables, parcelables.length, Productos[].class);

        ProductosAdapter adapter = new ProductosAdapter(mProductos);
        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //Esto solo se debe poner si nuestros datos van a tener un tamaño fijo
//        mRecyclerView.setHasFixedSize(true);

    }
}
