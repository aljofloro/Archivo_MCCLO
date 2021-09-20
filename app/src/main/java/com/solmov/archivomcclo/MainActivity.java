package com.solmov.archivomcclo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.solmov.archivomcclo.data.Expediente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

  private static final int REQUEST_CODE = 300;
  FloatingActionButton btn_leerCodigo;
  Button btn_generarCvs;
  EditText edt_anaquel,edt_ubicacion,edt_expedientes,edt_fichero;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btn_leerCodigo = findViewById(R.id.btn_leerCodigo);
    btn_generarCvs = findViewById(R.id.btn_grabar);
    edt_anaquel = findViewById(R.id.edt_anaquel);
    edt_ubicacion = findViewById(R.id.edt_espacio);
    edt_expedientes = findViewById(R.id.edt_expedientes);
    edt_fichero = findViewById(R.id.edt_fichero);

    btn_leerCodigo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
        integrator
            .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            .setPrompt("Lector de CB")
            .setCameraId(0)
            .setBeepEnabled(true)
            .setBarcodeImageEnabled(true)
            .initiateScan();
      }
    });

    btn_generarCvs.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final String fichero = edt_fichero.getText().toString();
        final String anaquel = edt_anaquel.getText().toString();
        final String espacio = edt_ubicacion.getText().toString();
        final String expedientes = edt_expedientes.getText().toString();
        guardarArchivo(fichero,anaquel,espacio,expedientes,getBaseContext());

        Toast.makeText(getBaseContext(), "Fichero Almacenado", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void guardarArchivo(String fichero,String anaquel,String espacio,String expedientes, Context context){
    String stadoSD = Environment.getExternalStorageState();
    if(!stadoSD.equals(Environment.MEDIA_MOUNTED)){
      Toast.makeText(context,"No es posible escribir en el almacenamietno externo", Toast.LENGTH_SHORT).show();
      return;
    }
    try{
      String texto = "id_anaquel,id_espacio,num_expediente\n";
      if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
          == PackageManager.PERMISSION_GRANTED){
        File archivo = new File(Environment.getExternalStorageDirectory()+"/"+fichero+".csv");
        FileOutputStream fileOutputStream = new FileOutputStream(archivo,true);
        String[] numExpedientes = expedientes.split(",");
        for(String expediente : numExpedientes){
          texto += "'"+anaquel+"','"+espacio+"','"+expediente+"'\n";
        }
        fileOutputStream.write(texto.getBytes());
        fileOutputStream.close();
        edt_fichero.setText("");
        edt_anaquel.setText("");
        edt_ubicacion.setText("");
        edt_expedientes.setText("");
      }else{
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                         int[] grantResults) {
    switch (requestCode) {
      case REQUEST_CODE:
        if (grantResults.length > 0 &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        } else {

        }
        return;
    }
  }

  protected void onActivityResult(int requestCode,int resultCode, Intent data){
    IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
    if(result != null){
      if(result.getContents() == null){
        Toast.makeText(getApplicationContext(), "Se canceló la lectura", Toast.LENGTH_SHORT).show();
      }else{
        Expediente expediente = new Expediente(result.getContents());
        edt_expedientes.setText(edt_expedientes.getText().toString()+","+expediente.getNumeroExpediente());
      }
    }else{
      super.onActivityResult(requestCode,resultCode,data);
    }
  }
}