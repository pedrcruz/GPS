package pt.isec.ans.tstrascunho;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class DesenhoActivity extends Activity {
    Desenho desenho;
    AreaDesenho ad;
    FrameLayout fr;
    String strTitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desenho);

        if ((savedInstanceState!=null && savedInstanceState.getBoolean("Gravado")) ||
             getIntent().getBooleanExtra("Editar",false))  {
            desenho = ((Aplicacao)getApplication()).save;
        } else {
            strTitulo = getIntent().getStringExtra("Titulo");
            if (strTitulo == null)
                strTitulo = "(sem titulo)";
            String strImage = getIntent().getStringExtra("ImagemFundo");
            if (strImage !=null)
                desenho = new Desenho(strTitulo,strImage);
            else {
                int cor = getIntent().getIntExtra("CorFundo", Color.RED);
                desenho = new Desenho(strTitulo, cor);
            }
            Aplicacao.addDesenho(desenho);
        }


        fr = (FrameLayout) findViewById(R.id.frAreaDesenho);
        ad = new AreaDesenho(this,desenho);
        fr.addView(ad);

        getActionBar().setTitle(desenho.strTitulo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_desenho,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuPreto:
                ad.setCorLinha(Color.BLACK);
                break;
            case R.id.menuVermelho:
                ad.setCorLinha(Color.RED);
                break;
            case R.id.menuVerde:
                ad.setCorLinha(Color.GREEN);
                break;
            case R.id.menuAzul:
                ad.setCorLinha(Color.BLUE);
                break;
            case R.id.menuBranco:
                ad.setCorLinha(Color.WHITE);
                break;
            case R.id.menuGravar:
                Aplicacao.gravar();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ((Aplicacao)getApplication()).save = desenho;
        outState.putBoolean("Gravado",true);
    }
}

class Ponto implements Serializable {
    float x,y;

    public Ponto(float x, float y) {
        this.x = x;
        this.y = y;
    }
}

class Linha implements Serializable{
    public ArrayList<Ponto> tabPontos;
    int corLinha = Color.BLACK;

    public Linha(int cor) {
        corLinha = cor;
        tabPontos = new ArrayList<>();
    }
}

class Desenho implements Serializable{
    String strTitulo;
    int corFundo;
    String imagemFundo;
    ArrayList<Linha> tabLinhas;
    Date dataCriacao;

    public Desenho(String strTitulo, int corFundo) {
        this.strTitulo = strTitulo;
        this.corFundo = corFundo;
        this.imagemFundo = null;
        this.tabLinhas = new ArrayList<>();
        dataCriacao = new Date();
    }
    public Desenho(String strTitulo, String imagemFundo) {
        this.strTitulo = strTitulo;
        this.corFundo = Color.WHITE;
        this.imagemFundo = imagemFundo;
        this.tabLinhas = new ArrayList<>();
        dataCriacao = new Date();
    }

    void addPonto(Ponto p) {
        tabLinhas.get(tabLinhas.size()-1).tabPontos.add(p);
    }
    void addLinha(int cor) {
        Linha linha = new Linha(cor);
        tabLinhas.add(linha);
    }
    boolean temLinhas() {
        return tabLinhas.size()>0;
    }
}
class AreaDesenho extends View implements GestureDetector.OnGestureListener{
    Desenho desenho;

    GestureDetector gd;
    Paint paint;
    int corLinha;

    void setCorLinha(int cor) {
        corLinha = cor;
    }

    public AreaDesenho(Context context, Desenho desenho) {
        super(context);
        this.desenho = desenho;
        corLinha = Color.BLACK;
        if (desenho.imagemFundo != null)
            Aplicacao.setPic(this,desenho.imagemFundo);

        gd = new GestureDetector(context, this);
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gd.onTouchEvent(event))
            return true;
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!desenho.temLinhas())
            return;

        float lastx=0,lasty=0;
        for(int i=0;i<desenho.tabLinhas.size();i++) {
            paint.setColor(desenho.tabLinhas.get(i).corLinha);
            for (int j = 0; j < desenho.tabLinhas.get(i).tabPontos.size(); j++) {
                float x = desenho.tabLinhas.get(i).tabPontos.get(j).x;
                float y = desenho.tabLinhas.get(i).tabPontos.get(j).y;
                if (j > 0)
                    canvas.drawLine(lastx, lasty, x, y, paint);
                lastx = x;
                lasty = y;
            }
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        desenho.addLinha(corLinha);
        desenho.addPonto(new Ponto(e.getX(0),e.getY(0)));
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        desenho.addPonto(new Ponto(e2.getX(0),e2.getY(0)));
        invalidate();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
