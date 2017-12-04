package pt.isec.ans.tstrascunho;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.lang.reflect.Method;

public class CameraActivity extends Activity {

    String imageFilePath = "/sdcard/temp.png";
    ImageView imagePreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }

        imagePreview = (ImageView) findViewById(R.id.imagePreview);
    }

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
            intent.putExtra("ImagemFundo", imageFilePath);
            intent.putExtra("Titulo",strTitulo);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCapturarImagem(View v) {
        String strTitulo = ((EditText)findViewById(R.id.edTitulo)).getText().toString();
        if (strTitulo.isEmpty()) {
            findViewById(R.id.edTitulo).requestFocus();
            return;
        }
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        imageFilePath = getExternalFilesDir(null)+"/"+strTitulo+".png";
        Uri fileUri;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = Uri.fromFile(new File(imageFilePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, 20);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TstRascunho","result 1");
        if(requestCode == 20 && resultCode==RESULT_OK){
            Log.d("TstRascunho","result 2");
            Aplicacao.setPic(imagePreview,imageFilePath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
