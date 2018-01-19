package pt.isec.ans.tstrascunho;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class HistoricoActivity extends Activity {

    ListView lstView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        getActionBar().hide();
        lstView = (ListView) findViewById(R.id.lstHistorico);
        HistoricoAdapter ha = new HistoricoAdapter(Aplicacao.getListaDesenhos());
        lstView.setAdapter(ha);
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HistoricoActivity.this,DesenhoActivity.class);

                ((Aplicacao)getApplication()).save=Aplicacao.getListaDesenhos().get(position);
                intent.putExtra("Editar",true);
                startActivity(intent);
                finish();
            }
        });
    }

    class HistoricoAdapter extends BaseAdapter {
        List<Desenho> lstDes;

        public HistoricoAdapter(List<Desenho> lst) {
            lstDes = lst;
        }

        @Override
        public int getCount() {
            return lstDes.size();
        }

        @Override
        public Object getItem(int position) {
            return lstDes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View layout = getLayoutInflater().inflate(R.layout.item_lista,null);
            Desenho des = lstDes.get(position);
            //((TextView) layout.findViewById(R.id.tvTitulo)).setText(des.strTitulo);

           ImageView img = ((ImageView) layout.findViewById(R.id.imagePreview));
           Bitmap bitmap = Bitmap.createScaledBitmap(Aplicacao.getListaDesenhos().get(position).bmp,200,200,true);
            img.setImageBitmap(bitmap);
            ((TextView) layout.findViewById(R.id.tvData)).setText(des.dataCriacao.toString());
            return layout;
        }
    }
}
