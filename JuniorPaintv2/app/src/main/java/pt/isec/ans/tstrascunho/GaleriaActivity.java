package pt.isec.ans.tstrascunho;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GaleriaActivity extends Activity {

    String imageFilePath=null;
    ImageView imagePreview;
    int n = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        copyAssets();

        imagePreview = (ImageView) findViewById(R.id.imagePreview);

        int imageArr[] = new int[n];
       /* imageArr[0] = R.drawable.image1;
        imageArr[1] = R.drawable.image2;
        imageArr[2] = R.drawable.image3;
        //imageArr[3] = R.drawable.image4;
        //imageArr[4] = R.drawable.image5;
        imageArr[5] = R.drawable.image6;*/

        int choosedphoto = (int)(Math.random()*n );

        imagePreview.setImageResource(imageArr[choosedphoto]);
        Intent intent = new Intent(this,DesenhoActivity.class);

        imageFilePath = new File(getFilesDir(),"image1.jpg").getAbsolutePath();

        Log.i("alo", imageFilePath);
        intent.putExtra("ImagemFundo", imageFilePath);
        intent.putExtra("Titulo","Titulo");
        startActivity(intent);
        finish();

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
                setImgFromAsset(imagePreview,"image1.jpg");//Para exemplificar assets... REMOVER
                return true;
            }
            Intent intent = new Intent(this,DesenhoActivity.class);
            intent.putExtra("ImagemFundo", "drawable://image1.jpg");
            intent.putExtra("Titulo",strTitulo);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void setImgFromAsset(ImageView mImageView,String strName)
    {
        AssetManager assetManager = getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
            Drawable d = Drawable.createFromStream(istr, null);
            mImageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onEscolherImagem(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 10 && data != null && data.getData() != null){
            Uri _uri = data.getData();

            if (_uri != null) {

                Cursor cursor = getContentResolver().query(_uri,
                        new String[] { "file" },
                        null, null, null);
                Log.i("ddd",cursor.toString());
                cursor.moveToFirst();

                imageFilePath = cursor.getString(0);
                Aplicacao.setPic(imagePreview,imageFilePath);
                cursor.close();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void copyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                File outFile = new File(getExternalFilesDir(null), filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}

