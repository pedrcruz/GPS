package pt.isec.ans.tstrascunho;

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

public class SolidoActivity extends Activity {

    SeekBar sbRed,sbGreen,sbBlue;
    FrameLayout previewCor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solido);

        sbRed   = (SeekBar) findViewById(R.id.sbRed);
        sbGreen = (SeekBar) findViewById(R.id.sbGreen);
        sbBlue  = (SeekBar) findViewById(R.id.sbBlue);

        sbRed.setMax(255);sbRed.setProgress(255);
        sbGreen.setMax(255);sbGreen.setProgress(255);
        sbBlue.setMax(255);sbBlue.setProgress(255);
        sbRed.setOnSeekBarChangeListener(procSeekBar);
        sbGreen.setOnSeekBarChangeListener(procSeekBar);
        sbBlue.setOnSeekBarChangeListener(procSeekBar);

        previewCor = (FrameLayout) findViewById(R.id.previewCor);
        previewCor.setBackgroundColor(Color.WHITE);

    }

    SeekBar.OnSeekBarChangeListener procSeekBar = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            previewCor.setBackgroundColor(Color.rgb(sbRed.getProgress(),sbGreen.getProgress(),sbBlue.getProgress()));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
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
