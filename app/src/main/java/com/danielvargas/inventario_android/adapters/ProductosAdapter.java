package com.danielvargas.inventario_android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danielvargas.inventario_android.R;
import com.danielvargas.inventario_android.model.Productos;

/**
 * Created by Daniel on 18/11/2017
 */

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder> {

    private Productos[] mProductos;

    public ProductosAdapter(Productos[] productos) {
        mProductos = productos;
    }

    @Override
    public ProductosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productos_list_individual, parent, false);
        ProductosViewHolder viewHolder = new ProductosViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductosViewHolder holder, int position) {
            holder.bindProductos(mProductos[position]);
    }

    @Override
    public int getItemCount() {
        return mProductos.length;
    }

    public class ProductosViewHolder extends RecyclerView.ViewHolder {

        public TextView mNombre;
        public TextView mPrecio;
        public TextView mPrecioE;
        public TextView mCantidad;

        public ProductosViewHolder(View itemView) {
            super(itemView);
            mNombre = (TextView) itemView.findViewById(R.id.nombre);
            mPrecio = (TextView) itemView.findViewById(R.id.precio);
            mPrecioE = (TextView) itemView.findViewById(R.id.precioE);
            mCantidad = (TextView) itemView.findViewById(R.id.cantidad);
        }

        public void bindProductos(Productos productos) {
            mNombre.setText(productos.getNombre());
            mPrecio.setText(productos.getPrecio() + "");
            mPrecioE.setText(productos.getPrecioEntrada() + "");
            mCantidad.setText(productos.getVendidos()+"");
        }
    }

}
