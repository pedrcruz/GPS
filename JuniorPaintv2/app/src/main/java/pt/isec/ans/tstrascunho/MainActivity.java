package pt.isec.ans.tstrascunho;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

    public class MainActivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            getActionBar().hide();
        }

        public void onSolido(View v) {

            Intent intent = new Intent(this,DesenhoActivity.class);
            intent.putExtra("CorFundo", Color.rgb(255,255,255));
            intent.putExtra("Titulo","titulo");
            startActivity(intent);
        }

    public void onGaleria(View v) {
       Intent intent = new Intent(this,GaleriaActivity.class);
        startActivity(intent);
    }
    public void onNovoJogo(View v) {
        Intent intent = new Intent(this,GaleriaActivity.class);
      // intent.putExtra("frAreaDesenho", set);
        startActivity(intent);
    }
    public void onCamera(View v) {
        Intent intent = new Intent(this,CameraActivity.class);
        startActivity(intent);
    }
    public void onHistorico(View v) {
        Intent intent = new Intent(this,HistoricoActivity.class);
        startActivity(intent);
    }
}
