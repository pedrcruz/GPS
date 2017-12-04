package pt.isec.ans.tstrascunho;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by ans on 18/10/2017.
 */

public class Aplicacao extends Application {
    public Desenho save;
    ArrayList<Desenho> lstDesenhos;
    private static Aplicacao obj;

    public Aplicacao() {
        obj = this;
        lstDesenhos = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ler();
    }

    public static ArrayList<Desenho> getListaDesenhos() {
        return obj.lstDesenhos;
    }

    public static void addDesenho(Desenho des) {
        obj.lstDesenhos.add(des);
        gravar();
    }

    public static void gravar() {
        try {
            FileOutputStream fos=obj.openFileOutput("desenhos.dat",MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj.lstDesenhos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void ler() {
        obj.lstDesenhos=null;
        try {
            FileInputStream fis=obj.openFileInput("desenhos.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Desenho> lst = (ArrayList<Desenho>) ois.readObject();
            obj.lstDesenhos=lst;
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (obj.lstDesenhos == null)
            obj.lstDesenhos = new ArrayList<>();
    }

    public static void setPic(ImageView mImageView, String mCurrentPhotoPath) {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions); // existem outros. Ex: decodeStream
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap); //em alternativa retornar apenas o Bitmap
    }

    public static void setPic(View view, String mCurrentPhotoPath) {
        // Get the dimensions of the View
        int targetW = view.getWidth();
        int targetH = view.getHeight();

        if (targetH==0 || targetW == 0) {
            WindowManager wm = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            targetW = metrics.widthPixels;
            targetH = metrics.heightPixels;
        }

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions); // existem outros. Ex: decodeStream
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        BitmapDrawable bd = new BitmapDrawable(view.getResources(),bitmap);
        view.setBackground(bd);
    }

}
