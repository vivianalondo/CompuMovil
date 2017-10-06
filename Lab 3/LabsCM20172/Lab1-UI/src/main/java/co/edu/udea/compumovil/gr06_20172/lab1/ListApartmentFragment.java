package co.edu.udea.compumovil.gr06_20172.lab1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ListApartmentFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avator;
        public TextView name;
        public TextView description;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.apartment_list, parent, false));
            avator = (ImageView) itemView.findViewById(R.id.list_avatar);
            name = (TextView) itemView.findViewById(R.id.list_title);
            description = (TextView) itemView.findViewById(R.id.list_desc);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    //intent.putExtra("variable_string", (String)name.getText());
                    intent.putExtra(DetailActivity.EXTRA_NOMBRE, (String)name.getText());
                    Log.d("TAGO",  (String)name.getText());
                    context.startActivity(intent);
                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        int LENGTH = 0;
        DbHelper dbHelper;
        SQLiteDatabase db;
        View view;

        private final Bitmap[] mPlaceAvators;
        ArrayList<String> mPlaces = new ArrayList(), mPlaceDesc = new ArrayList();
        ArrayList avat = new ArrayList();
        boolean control=false;


        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            dbHelper =new DbHelper(getActivity().getBaseContext());
            db= dbHelper.getReadableDatabase();
            Cursor test=db.rawQuery("select * from "+StatusContract.TABLE_APARTMENT+" order by "+ StatusContract.Column_Apartment.NAME, null);

            if (test.moveToFirst()) {
                do{
                    mPlaces.add(test.getString(1));
                    mPlaceDesc.add(test.getString(3));
                    avat.add(test.getBlob(7));
                }while(test.moveToNext());
                control=true;
            } else{
                Toast.makeText(getActivity().getBaseContext(),"Sin Apartamentos",Toast.LENGTH_LONG).show();
            }
            db.close();
            LENGTH = mPlaces.size();

            mPlaceAvators = new Bitmap[avat.size()];
            for (int i = 0; i < mPlaceAvators.length; i++) {
                mPlaceAvators[i] = BitmapFactory.decodeByteArray((byte[]) avat.get(i), 0, ((byte[]) avat.get(i)).length);
            }

        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.avator.setImageBitmap(mPlaceAvators[position % mPlaceAvators.length]);
            holder.name.setText(mPlaces.get(position % mPlaces.size()));
            holder.description.setText(mPlaceDesc.get(position % mPlaceDesc.size()));
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}
