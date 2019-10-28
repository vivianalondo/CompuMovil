package co.edu.udea.compumovil.gr06_20172.lab1.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.List;

import co.edu.udea.compumovil.gr06_20172.lab1.DetailActivity;
import co.edu.udea.compumovil.gr06_20172.lab1.POJO.Apartment;
import co.edu.udea.compumovil.gr06_20172.lab1.R;

/**
 * Created by Viviana Londoño on 6/10/2017.
 */

public class ApartmentsAdapter extends RecyclerView.Adapter<ApartmentsAdapter.ApartmentViewHolder> {

    private List<Apartment> apartments;
    private int rowLayout;
    private Context context;


    public static class ApartmentViewHolder extends RecyclerView.ViewHolder {
        LinearLayout apartmentsLayout;
        TextView numberApartment;
        TextView apartmentTitle;
        TextView type;
        TextView apartmentDescription;
        ImageView avator;


        public ApartmentViewHolder(View v) {
            super(v);
            apartmentsLayout = (LinearLayout) v.findViewById(R.id.apartments_layout);
            numberApartment = (TextView) v.findViewById(R.id.numberApartment);
            apartmentTitle = (TextView) v.findViewById(R.id.title);
            type = (TextView) v.findViewById(R.id.type);
            apartmentDescription = (TextView) v.findViewById(R.id.description);
            avator = (ImageView) itemView.findViewById(R.id.rating_image);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    //intent.putExtra("variable_string", (String)name.getText());
                    intent.putExtra(DetailActivity.EXTRA_NOMBRE, (String)numberApartment.getText());
                    Log.d("TAGO",  (String)numberApartment.getText());
                    context.startActivity(intent);
                }
            });

        }
    }

    public ApartmentsAdapter(List<Apartment> apartments, int rowLayout, Context context) {
        this.apartments = apartments;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ApartmentsAdapter.ApartmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ApartmentViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ApartmentViewHolder holder, final int position) {

        //Cursor test = db.rawQuery("select * from "+StatusContract.TABLE_APARTMENT+" order by "+ StatusContract.Column_Apartment.NAME, null);

        //Cursor test = apartments;

        Bitmap[] mPlaceAvators;
        byte[] avat = new byte[0];
        mPlaceAvators = new Bitmap[apartments.size()];

        System.out.println("Valor de picture: "+apartments.get(position).getPicture());
        if (apartments.get(position).getPicture()!=null){
            System.out.println("Valor de picture: "+apartments.get(position).getPicture());
            //holder.avator.setImageDrawable(apartments.get(position).getPicture().getBytes());
            avat = apartments.get(position).getPicture().getBytes();

            byte[] decodedString = Base64.decode(avat, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            ByteArrayInputStream imageStream = new ByteArrayInputStream(avat);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);

            //Bitmap bitmap = BitmapFactory.decodeByteArray(avat, 0, avat.length);
            holder.avator.setImageBitmap(decodedByte);
        }
        else{

            holder.avator.setImageResource(R.drawable.iconoapartamento);
        }
        //holder.avator.setImageBitmap(mPlaceAvators[position % mPlaceAvators.length]);
        holder.numberApartment.setText(apartments.get(position).getId().toString());
        holder.apartmentTitle.setText(apartments.get(position).getName());
        holder.type.setText(apartments.get(position).getApType());
        holder.apartmentDescription.setText(apartments.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        int sizel = 0;
        if (apartments != null){
            sizel = apartments.size();
        }
        return sizel;
    }


    public Apartment removeItem(int position) {
        final Apartment model = apartments.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Apartment model) {
        apartments.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Apartment model = apartments.remove(fromPosition);
        apartments.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<Apartment> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Apartment> newModels) {
        for (int i = apartments.size() - 1; i >= 0; i--) {
            final Apartment model = apartments.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Apartment> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Apartment model = newModels.get(i);
            if (!apartments.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Apartment> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Apartment model = newModels.get(toPosition);
            final int fromPosition = apartments.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
}

