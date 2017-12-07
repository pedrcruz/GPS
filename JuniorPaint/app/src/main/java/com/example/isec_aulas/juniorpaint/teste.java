package com.example.isec_aulas.juniorpaint;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;

public class teste extends Activity {

    SeekBar sbRed,sbGreen,sbBlue;
    FrameLayout previewCor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  //      previewCor = (FrameLayout) findViewById(R.id.previewCor);
  //      previewCor.setBackgroundColor(Color.WHITE);
        Intent intent = new Intent(this, DesenhoActivity.class);
        intent.putExtra("CorFundo", Color.rgb(255, 255, 255));
        intent.putExtra("Titulo", "Teste");
        startActivity(intent);
        finish();
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = new MenuInflater(this);
        mi.inflate(R.menu.menu_criar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menuCriar) {
            String strTitulo = ((EditText)findViewById(R.id.edTitulo)).getText().toString();
            if (strTitulo.isEmpty()) {
                findViewById(R.id.edTitulo).requestFocus();
                return true;
            }
            Intent intent = new Intent(this,DesenhoActivity.class);
            intent.putExtra("CorFundo",Color.rgb(sbRed.getProgress(),sbGreen.getProgress(),sbBlue.getProgress()));
            intent.putExtra("Titulo",strTitulo);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}