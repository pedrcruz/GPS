package pt.isec.ans.tstrascunho;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
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

public class DesenhoActivity extends Activity implements View.OnClickListener {
    int currentColorState = 99;
    Desenho desenho;
    AreaDesenho ad;
    FrameLayout fr;
    String strTitulo;
    ImageView carimbo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desenho);

//IMAGE VIEW(borracha, lápis, balde)
        ImageView balde = ((ImageView) this.findViewById(R.id.balde));
        ImageView borracha = ((ImageView) this.findViewById(R.id.borracha));
        ImageView lapis = ((ImageView) this.findViewById(R.id.lapis));
        //
        balde.setOnClickListener(this);
        borracha.setOnClickListener(this);
        lapis.setOnClickListener(this);
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

        getActionBar().setTitle(desenho.strTitulo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_desenho, menu);
        return true;
    }

    //LIXO
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


    class Carimbo implements Serializable {
        ImageView img;

        public Carimbo(ImageView img) {

        }

        public void putCarimbo(String img) {

        }
    }
        //EVENTOS DE CLIQUES NA ATIVIDADE DE DESENHO
        public void onClick(View view) {
            switch (view.getId()) {//dá o ID da imageView
                case R.id.balde:
                    seleccionaFerramenta("balde");
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
                    if (currentColorState != 99 || currentColorState != Color.WHITE)
                        ad.setCorLinha(currentColorState);//Carrega a cor que estava a usar antes de escolher a borracha
                    ad.setTamanhoLinha(5);
                    break;
                case R.id.cor1:

                    break;
            }
        }


    }

    class Ponto implements Serializable {
        float x, y;

        public Ponto(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    class Linha implements Serializable {
        public ArrayList<Ponto> tabPontos;
        int corLinha = Color.BLACK;
        int tamLinha = 5;

        public Linha(int cor, int tamLinha) {
            corLinha = cor;
            this.tamLinha = tamLinha;
            tabPontos = new ArrayList<>();
        }
    }

    class Desenho implements Serializable {
        String strTitulo;
        int corFundo;
        String imagemFundo;
        ArrayList<Linha> tabLinhas;
        Date dataCriacao;
        ImageView carimbo;


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

        public void setCarimbo(ImageView v) {
            this.carimbo = v;
        }

        void addPonto(Ponto p) {
            tabLinhas.get(tabLinhas.size() - 1).tabPontos.add(p);
        }

        void addLinha(int cor, int tamLinha) {
            Linha linha = new Linha(cor, tamLinha);
            tabLinhas.add(linha);
        }

        void addCarimbo() {
            if (carimbo != null) {

            }
        }

        boolean temLinhas() {
            return tabLinhas.size() > 0;
        }
    }

    class AreaDesenho extends View implements GestureDetector.OnGestureListener {
        Desenho desenho;

        GestureDetector gd;
        Paint paint;
        int corLinha;
        int tamanhoLinha;

        void setCorLinha(int cor) {
            corLinha = cor;
        }

        void setTamanhoLinha(int tamLinha) {
            this.tamanhoLinha = tamLinha;
        }

        public AreaDesenho(Context context, Desenho desenho) {
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

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (gd.onTouchEvent(event)) {
                Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.borracha);
                Canvas canvas = new Canvas();
                Bitmap indexcanvas = Bitmap.createScaledBitmap(myBitmap, 450, 450, true);
                canvas.drawBitmap(myBitmap, event.getX(), event.getY(), null);
                invalidate();
                return true;
            }
            return super.onTouchEvent(event);
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if (!desenho.temLinhas())
                return;
            // if(desenho.carimbo != null) {
            Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.c_barco);
            float canvasx = (float) getWidth();
            float canvasy = (float) getHeight();
            ;

            //    Bitmap indexcanvas = Bitmap.createScaledBitmap(myBitmap, 450, 450, true);
            float bitmapx = (float) myBitmap.getWidth();
            float bitmapy = (float) myBitmap.getHeight();
            float boardPosX = (canvasx - bitmapx) / 2;
            float boardPosY = (canvasy - bitmapy) / 2;

            //      canvas.drawBitmap(indexcanvas, boardPosX, boardPosY, paint);
            //       invalidate();
            //c.drawRect(r, paint);
            //       canvas.drawBitmap(myBitmap, null, rectangule, paint);
            //    canvas.drawBitmap(myBitmap,getPivotX(),getPivotY() , null);

            //   }
            float lastx = 0, lasty = 0;
            for (int i = 0; i < desenho.tabLinhas.size(); i++) {
                paint.setColor(desenho.tabLinhas.get(i).corLinha);
                paint.setStrokeWidth(desenho.tabLinhas.get(i).tamLinha);
                for (int j = 0; j < desenho.tabLinhas.get(i).tabPontos.size(); j++) {
                    float x = desenho.tabLinhas.get(i).tabPontos.get(j).x;
                    float y = desenho.tabLinhas.get(i).tabPontos.get(j).y;

                    if (j > 0)
                        canvas.drawLine(lastx, lasty, x, y, paint);
                    Bitmap indexcanvas = Bitmap.createScaledBitmap(myBitmap, 450, 450, true);
                    canvas.drawBitmap(indexcanvas, x, y, paint);
                    invalidate();

                    lastx = x;
                    lasty = y;
                }
            }
        }

        @Override
        public boolean onDown(MotionEvent e) {
            desenho.addLinha(corLinha, tamanhoLinha);
            desenho.addPonto(new Ponto(e.getX(0), e.getY(0)));

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
    }


