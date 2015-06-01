package com.example.randy.textoavoz;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.content.Intent;
import java.util.Locale;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, OnInitListener {

    private TextToSpeech miTexto;
    private int CodigoDatos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Definimos el botón:
        Button botonHablar=(Button)findViewById(R.id.hablar);
        botonHablar.setOnClickListener(this);

        Intent chequeandoTTSIntent = new Intent();
        chequeandoTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(chequeandoTTSIntent, CodigoDatos);
    }

    public void onClick(View v){
        EditText TextoIngresado = (EditText)findViewById(R.id.entrar);
        String palabras = TextoIngresado.getText().toString();
        HablarPalabras(palabras);
    }

    private void HablarPalabras(String speech){
        miTexto.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode==CodigoDatos){
            if (requestCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                miTexto = new TextToSpeech(this,this);
            }
            else
            {
                Intent InstalarTTSIntent = new Intent();
                InstalarTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(InstalarTTSIntent);
            }
        }
    }

    public void onInit(int StatusInicial){
        if (StatusInicial == TextToSpeech.SUCCESS){
            if(miTexto.isLanguageAvailable(Locale.US)== TextToSpeech.LANG_AVAILABLE){
                miTexto.setLanguage(Locale.US);
            }
        }
        else if(StatusInicial==TextToSpeech.ERROR){
            Toast.makeText(this, "Sorry!!", Toast.LENGTH_LONG).show();
        }
    }

}
