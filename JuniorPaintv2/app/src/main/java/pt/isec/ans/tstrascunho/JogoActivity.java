package pt.isec.ans.tstrascunho;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class JogoActivity extends Activity implements View.OnClickListener {
    int currentColorState = 99;
    Desenho desenho;
    AreaDesenho ad;
    FrameLayout fr;
    String strTitulo;
    ImageView carimbo1,carimbo2,carimbo3,carimbo4,carimbo5,carimbo6,carimbo7;
    Button save,home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);

//IMAGE VIEW(borracha, lápis, balde)
        ImageView balde = ((ImageView) this.findViewById(R.id.balde));
        ImageView borracha = ((ImageView) this.findViewById(R.id.borracha));
        ImageView lapis = ((ImageView) this.findViewById(R.id.lapis));
//SAVE & HOME
        save = findViewById(R.id.button);
        home = findViewById(R.id.button2);
//CARIMBOS ( 1 A 7)
        carimbo1 =  findViewById(R.id.carimbo1);
        carimbo2 =  findViewById(R.id.carimbo2);
        carimbo3 =  findViewById(R.id.carimbo3);
        carimbo4 =  findViewById(R.id.carimbo4);
        carimbo5 =  findViewById(R.id.carimbo5);
        carimbo6 =  findViewById(R.id.carimbo6);
        carimbo7 =  findViewById(R.id.carimbo7);
//LISTENERS
        balde.setOnClickListener(this);
        borracha.setOnClickListener(this);
        lapis.setOnClickListener(this);

        save.setOnClickListener(this);
        home.setOnClickListener(this);

        carimbo1.setOnClickListener(this);
        carimbo2.setOnClickListener(this);
        carimbo3.setOnClickListener(this);
        carimbo4.setOnClickListener(this);
        carimbo5.setOnClickListener(this);
        carimbo6.setOnClickListener(this);
        carimbo7.setOnClickListener(this);

        fr.getBackground();
//------------------------------------
        if ((savedInstanceState != null && savedInstanceState.getBoolean("Gravado")) ||
                getIntent().getBooleanExtra("Editar", false)) {
            desenho = ((Aplicacao) getApplication()).save;
        } else {
            strTitulo = getIntent().getStringExtra("Titulo");
            if (strTitulo == null)
                strTitulo = "(sem titulo)";
            String strImage = getIntent().getStringExtra("ImagemFundo");
            if (strImage != null)
                desenho = new Desenho(strTitulo, strImage);
            else {
                int cor = getIntent().getIntExtra("CorFundo", Color.RED);
                desenho = new Desenho(strTitulo, cor);
            }
            Aplicacao.addDesenho(desenho);
        }

        carimbo1 = findViewById(R.id.carimbo1);
        fr = (FrameLayout) findViewById(R.id.frAreaDesenho);
        ad = new AreaDesenho(this, desenho);
        fr.addView(ad);

        //   getActionBar().setTitle("Desenho");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ((Aplicacao) getApplication()).save = desenho;
        outState.putBoolean("Gravado", true);
    }

    public void onChoosingColor(View v) {
        //Cor 1
        TextView tvCorSelecionada = null;
        ColorDrawable cd;
//SE A BORRACHA NAO ESTIVER SELECCIONADA, MUDA A COR
        if (!findViewById(R.id.borracha).isSelected()) {
//PROCURA a TextView Seleccionada
            if (findViewById(R.id.cor1).isPressed() == true) //cor 1
                tvCorSelecionada = findViewById(R.id.cor1);
            else if (findViewById(R.id.cor2).isPressed() == true) //cor 2
                tvCorSelecionada = findViewById(R.id.cor2);
            else if (findViewById(R.id.cor3).isPressed() == true) //cor 3
                tvCorSelecionada = findViewById(R.id.cor3);
            else if (findViewById(R.id.cor4).isPressed() == true) //cor 4
                tvCorSelecionada = findViewById(R.id.cor4);
            else if (findViewById(R.id.cor5).isPressed() == true) //cor 5
                tvCorSelecionada = findViewById(R.id.cor5);
            else if (findViewById(R.id.cor6).isPressed() == true) //cor 6
                tvCorSelecionada = findViewById(R.id.cor6);
            else if (findViewById(R.id.cor7).isPressed() == true) //cor 7
                tvCorSelecionada = findViewById(R.id.cor7);
            else if (findViewById(R.id.cor8).isPressed() == true) //cor 8
                tvCorSelecionada = findViewById(R.id.cor8);
            else if (findViewById(R.id.cor9).isPressed() == true) //cor 9
                tvCorSelecionada = findViewById(R.id.cor9);
            else if (findViewById(R.id.cor10).isPressed() == true) //cor 10
                tvCorSelecionada = findViewById(R.id.cor10);
            else if (findViewById(R.id.cor11).isPressed() == true) //cor 11
                tvCorSelecionada = findViewById(R.id.cor11);
            else if (findViewById(R.id.cor12).isPressed() == true) //cor 12
                tvCorSelecionada = findViewById(R.id.cor12);

            cd = (ColorDrawable) tvCorSelecionada.getBackground();//busca a cor do background
            ad.setCorLinha(cd.getColor());                        //atribui a cor da TV ao lápis

        }
    }

    public void seleccionaFerramenta(String nome) {
        if (nome.equals("borracha")) {
            findViewById(R.id.borracha).setSelected(true);
            findViewById(R.id.lapis).setSelected(false);
            findViewById(R.id.balde).setSelected(false);
            findViewById(R.id.borracha).setBackgroundColor(Color.BLUE);
            findViewById(R.id.lapis).setBackgroundColor(Color.GRAY);
            findViewById(R.id.balde).setBackgroundColor(Color.GRAY);
        } else if (nome.equals("lapis")) {
            findViewById(R.id.borracha).setSelected(false);
            findViewById(R.id.lapis).setSelected(true);
            findViewById(R.id.balde).setSelected(false);
            findViewById(R.id.lapis).setBackgroundColor(Color.BLUE);
            findViewById(R.id.balde).setBackgroundColor(Color.GRAY);
            findViewById(R.id.borracha).setBackgroundColor(Color.GRAY);
        } else if (nome.equals("balde")) {
            findViewById(R.id.borracha).setSelected(false);
            findViewById(R.id.lapis).setSelected(false);
            findViewById(R.id.balde).setSelected(true);
            findViewById(R.id.balde).setBackgroundColor(Color.BLUE);
            findViewById(R.id.lapis).setBackgroundColor(Color.GRAY);
            findViewById(R.id.borracha).setBackgroundColor(Color.GRAY);
        }
    }

    public void onChoosingFerramentaDesenho(View v) {


        if (findViewById(R.id.balde).isPressed() == true) {
            /*seleccionaFerramenta("balde");*/

        } else if (findViewById(R.id.borracha).isPressed() == true) {
            /*seleccionaFerramenta("borracha");

            currentColorState= ad.paint.getColor();//Guarda a cor que está a ser usada
            ad.paint.setStrokeWidth(20);
            ad.setCorLinha(Color.WHITE);
            ad.paint.setStyle(Paint.Style.FILL);*/
        } else if (findViewById(R.id.lapis).isPressed() == true) {
           /* seleccionaFerramenta("lapis");
            if(currentColorState != 99)
                ad.paint.setColor(currentColorState);//Carrega a cor que estava a usar antes de escolher a borracha
            ad.paint.setStrokeWidth(5);
            ad.paint.setStyle(Paint.Style.FILL);*/
        }

    }
    public void getcarimbo() {
    }



    //EVENTOS DE CLIQUES NA ATIVIDADE DE DESENHO
    public void onClick(View view) {
        switch (view.getId()) {//dá o ID da imageView
            case R.id.balde:
                seleccionaFerramenta("balde");
                if (currentColorState != 99 && currentColorState != Color.WHITE)
                    ad.setCorLinha(currentColorState);//Carrega a cor que estava a usar antes de escolher a borracha
                ad.paint.setStrokeWidth(50);
                ad.setTamanhoLinha(50);
                break;
            case R.id.borracha:
                seleccionaFerramenta("borracha");

                currentColorState = ad.paint.getColor();//Guarda a cor que está a ser usada
                ad.paint.setStrokeWidth(20);
                ad.setCorLinha(Color.WHITE);
                ad.setTamanhoLinha(20);
                break;
            case R.id.lapis:
                seleccionaFerramenta("lapis");
                if (currentColorState != 99 && currentColorState != Color.WHITE)
                    ad.setCorLinha(currentColorState);//Carrega a cor que estava a usar antes de escolher a borracha
                ad.setTamanhoLinha(5);
                break;
            case R.id.carimbo1:
                ad.setCarimbo(R.mipmap.carimbo1);

                break;
            case R.id.carimbo2:
                ad.setCarimbo(R.mipmap.carimbo2);
                break;
            case R.id.carimbo3:
                ad.setCarimbo(R.mipmap.carimbo3);
                break;
            case R.id.carimbo4:
                ad.setCarimbo(R.mipmap.carimbo4);
                break;
            case R.id.carimbo5:
                ad.setCarimbo(R.mipmap.carimbo5);
                break;
            case R.id.carimbo6:
                ad.setCarimbo(R.mipmap.carimbo6);
                break;
            case R.id.carimbo7:
                ad.setCarimbo(R.mipmap.carimbo7);
                break;
            case R.id.button:
                Aplicacao.gravar();
                finish();
                break;
            case R.id.button2:
                finish();
                break;
        }
    }


}


class Carimbo1 implements Serializable {
    float x, y;

    public Carimbo1(float x, float y) {
        this.x = x;
        this.y = y;
    }

}

class Ponto1 implements Serializable {
    float x, y;

    public Ponto1(float x, float y) {
        this.x = x;
        this.y = y;
    }
}

class Linha1 implements Serializable {
    public ArrayList<Ponto> tabPontos;
    int corLinha = Color.BLACK;
    int tamLinha = 5;

    public Linha1(int cor, int tamLinha) {
        corLinha = cor;
        this.tamLinha = tamLinha;
        tabPontos = new ArrayList<>();
    }
}

class Desenho1 implements Serializable {
    String strTitulo;
    int corFundo;
    String imagemFundo;
    ArrayList<Linha> tabLinhas;
    Date dataCriacao;

    int x,y;

    public Desenho1(String strTitulo, int corFundo) {
        this.strTitulo = strTitulo;
        this.corFundo = corFundo;
        this.imagemFundo = null;
        this.tabLinhas = new ArrayList<>();
        dataCriacao = new Date();
        //   IdCarimbo = R.mipmap.carimbo1;
    }
    public Desenho1(String strTitulo, String imagemFundo) {
        this.strTitulo = strTitulo;
        this.corFundo = Color.WHITE;
        this.imagemFundo = imagemFundo;
        this.tabLinhas = new ArrayList<>();
        dataCriacao = new Date();

    }



    void addPonto(Ponto p) {
        tabLinhas.get(tabLinhas.size() - 1).tabPontos.add(p);
    }

    void addLinha(int cor, int tamLinha) {
        Linha linha = new Linha(cor, tamLinha);
        tabLinhas.add(linha);
    }

    void addCarimbo(Carimbo c) {
        if (c != null) {
            this.x = (int)c.x;
            this.y = (int)c.y;
        }
    }

    boolean temLinhas() {
        return tabLinhas.size() > 0;
    }
}

class AreaDesenho1 extends View implements GestureDetector.OnGestureListener {
    Desenho desenho;

    GestureDetector gd;
    Paint paint;
    int corLinha;
    int tamanhoLinha;
    int IdCarimbo;
    Bitmap indexcanvas;
    void setCorLinha(int cor) {
        corLinha = cor;
    }

    void setTamanhoLinha(int tamLinha) {
        this.tamanhoLinha = tamLinha;
    }
    public void setCarimbo(int IdCarimbo) {
        this.IdCarimbo = IdCarimbo;
    }


    public AreaDesenho1(Context context, Desenho desenho) {
        super(context);
        this.desenho = desenho;
        corLinha = Color.BLACK;
        tamanhoLinha = 5;
        if (desenho.imagemFundo != null)
            Aplicacao.setPic(this, desenho.imagemFundo);

        gd = new GestureDetector(context, this);
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        IdCarimbo = 0;
        indexcanvas = null;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gd.onTouchEvent(event)) {
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float bitmapx, bitmapy, boardPosX = desenho.x-255,boardPosY = desenho.y-255;


        if (!desenho.temLinhas())
            return;
        // if(desenho.carimbo != null) {
        if (IdCarimbo != 0) {
            Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), IdCarimbo);


            indexcanvas = Bitmap.createScaledBitmap(myBitmap, 450, 450, true);
            IdCarimbo = 0;

        }



        float lastx = 0, lasty = 0;
        for (int i = 0; i < desenho.tabLinhas.size(); i++) {
            paint.setColor(desenho.tabLinhas.get(i).corLinha);
            paint.setStrokeWidth(desenho.tabLinhas.get(i).tamLinha);
            for (int j = 0; j < desenho.tabLinhas.get(i).tabPontos.size(); j++) {
                float x = desenho.tabLinhas.get(i).tabPontos.get(j).x;
                float y = desenho.tabLinhas.get(i).tabPontos.get(j).y;

                if (j > 0)
                    canvas.drawLine(lastx, lasty, x, y, paint);

                lastx = x;
                lasty = y;
            }
        }
        if(indexcanvas != null )
            canvas.drawBitmap(indexcanvas, boardPosX, boardPosY, paint);


    }


    @Override
    public boolean onDown(MotionEvent e) {/*
        desenho.addLinha(corLinha,tamanhoLinha);
        desenho.addPonto(new Ponto(e.getX(0),e.getY(0)));

        return true;*/
        // if(findViewById(R.id.balde).isSelected()==true){
        /*final Point p1 = new Point();
        p1.x=(int) x; //x co-ordinate where the user touches on the screen
        p1.y=(int) y; //y co-ordinate where the user touches on the screen

        FloodFill f= new FloodFill();
        f.floodFill(bmp,pt,targetColor,replacementColor);*/
        //    desenho.addCarimbo(R.id.carimbo1);<--este método é o tal q tás a fzr para desenhar o carimbo Por exemplo
        // }
        //else{
        desenho.addLinha(corLinha,tamanhoLinha);
        desenho.addPonto(new Ponto(e.getX(0),e.getY(0)));
        if(IdCarimbo != 0)
            desenho.addCarimbo(new Carimbo(e.getX(0),e.getY(0)));
        //}
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }




    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        desenho.addPonto(new Ponto(e2.getX(0), e2.getY(0)));

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




    //Função do BALDE
    private void FloodFill(Bitmap bmp, Point pt, int targetColor, int replacementColor){
        Queue<Point> q = new LinkedList<Point>();
        q.add(pt);
        while (q.size() > 0) {
            Point n = q.poll();
            if (bmp.getPixel(n.x, n.y) != targetColor)
                continue;

            Point w = n, e = new Point(n.x + 1, n.y);
            while ((w.x > 0) && (bmp.getPixel(w.x, w.y) == targetColor)) {
                bmp.setPixel(w.x, w.y, replacementColor);
                if ((w.y > 0) && (bmp.getPixel(w.x, w.y - 1) == targetColor))
                    q.add(new Point(w.x, w.y - 1));
                if ((w.y < bmp.getHeight() - 1)
                        && (bmp.getPixel(w.x, w.y + 1) == targetColor))
                    q.add(new Point(w.x, w.y + 1));
                w.x--;
            }
            while ((e.x < bmp.getWidth() - 1)
                    && (bmp.getPixel(e.x, e.y) == targetColor)) {
                bmp.setPixel(e.x, e.y, replacementColor);

                if ((e.y > 0) && (bmp.getPixel(e.x, e.y - 1) == targetColor))
                    q.add(new Point(e.x, e.y - 1));
                if ((e.y < bmp.getHeight() - 1)
                        && (bmp.getPixel(e.x, e.y + 1) == targetColor))
                    q.add(new Point(e.x, e.y + 1));
                e.x++;
            }
        }}
}





