package co.edu.udea.compumovil.gr06_20172.lab1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.edu.udea.compumovil.gr06_20172.lab1.POJO.Apartment;
import co.edu.udea.compumovil.gr06_20172.lab1.adapter.ApartmentsAdapter;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiClient;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListApartmentFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        //Llamado a la api
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Apartment>> call = apiService.getData();
        call.enqueue(new Callback<List<Apartment>>() {
            @Override
            public void onResponse(Call<List<Apartment>> call1, Response<List<Apartment>> response) {

                List<Apartment> apartments = response.body();
                recyclerView.setAdapter(new ApartmentsAdapter(apartments, R.layout.list_item_apartment, getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<List<Apartment>> call1, Throwable t) {

                Log.e("Error", "Hay un error en el call apartment");
            }
        });
        return recyclerView;
    }

    /*public static class ViewHolder extends RecyclerView.ViewHolder {
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
    }*/


    /**
     * Adapter to display recycler view.

    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        int LENGTH = 0;
        DbHelper dbHelper;
        SQLiteDatabase db;
        View view;

        //codigo nuevo hasta el espacio
        private List<Apartment> apartments;
        private int rowLayout;

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
    }*/
}
