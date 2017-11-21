package com.danielvargas.inventario_android.service;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.danielvargas.inventario_android.R;

public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getActivity(); //esto es para saber de que actividad estamos hablando
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(R.string.error_titile) //este va a ser el titulo del mensaje. Todos estos valores de string los pase a string resourses (como debe ser)
                .setMessage(R.string.error_message)// esta va a ser el mensaje
                .setPositiveButton(R.string.error_ok_button_text, null); // este es el boton, ponemos null como lo que hace para que al darle click simplemente se cierre
        AlertDialog dialog = builder.create();
        return dialog;
    }
}

