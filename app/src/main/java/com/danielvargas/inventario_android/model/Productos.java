package com.danielvargas.inventario_android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daniel on 5/11/2017
 */

public class Productos implements Parcelable {
    private int id;
    private String mNombre;
    private double mPrecio;
    private double mPrecioEntrada;
    private String mDescripcion;
    private int mVendidos;


    public Productos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String nombre) {
        this.mNombre = nombre;
    }

    public double getPrecio() {
        return mPrecio;
    }

    public void setPrecio(double precio) {
        this.mPrecio = precio;
    }

    public double getPrecioEntrada() {
        return mPrecioEntrada;
    }

    public void setPrecioEntrada(double precioEntrada) {
        this.mPrecioEntrada = precioEntrada;
    }

    public String getDescripcion() {
        return mDescripcion;
    }

    public void setDescripcion(String descripcion) {
        this.mDescripcion = descripcion;
    }

    public int getVendidos() {
        return mVendidos;
    }

    public void setVendidos(int vendidos) {
        this.mVendidos = vendidos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(mNombre);
        dest.writeDouble(mPrecio);
        dest.writeDouble(mPrecioEntrada);
        dest.writeString(mDescripcion);
        dest.writeInt(mVendidos);
    }

    private Productos(Parcel in) {
        id = in.readInt();
        mNombre = in.readString();
        mPrecio = in.readDouble();
        mPrecioEntrada = in.readDouble();
        mDescripcion = in.readString();
        mVendidos = in.readInt();
    }

    public static final Creator<Productos> CREATOR = new Creator<Productos>() {
        @Override
        public Productos createFromParcel(Parcel in) {
            return new Productos(in);
        }

        @Override
        public Productos[] newArray(int size) {
            return new Productos[size];
        }
    };

}
