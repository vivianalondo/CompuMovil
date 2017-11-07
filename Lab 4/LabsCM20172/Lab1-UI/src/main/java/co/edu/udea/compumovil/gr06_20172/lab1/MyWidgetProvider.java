package co.edu.udea.compumovil.gr06_20172.lab1;

/**
 * Created by viviana on 7/11/17.
 */

import java.util.Random;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.RemoteViews;

import co.edu.udea.compumovil.gr06_20172.lab1.POJO.Apartment;
import co.edu.udea.compumovil.gr06_20172.lab1.POJO.User;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiClient;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWidgetProvider extends AppWidgetProvider {

    RemoteViews remoteViews;
    Context context1;
    int[] appWidgetIds1;
    AppWidgetManager appWidgetManager1;
    int id;

    private static final String ACTION_CLICK = "ACTION_CLICK";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        context1 = context;
        appWidgetIds1 = appWidgetIds;
        appWidgetManager1 = appWidgetManager;

        // Get all ids
        ComponentName thisWidget = new ComponentName(context,
                MyWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {
            // create some random data
            int number = (new Random().nextInt(100));
            id = widgetId;

            remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);
            Log.w("WidgetExample", String.valueOf(number));

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Apartment> call = apiService.getApartmentDetails(17);

            call.enqueue(new Callback<Apartment>() {
                @Override
                public void onResponse(Call<Apartment> call, Response<Apartment> response) {
                    if(response.isSuccessful()) {
                        Apartment apartment = response.body();
                        System.out.println("widget "+response.body().toString());
                        System.out.println("widget name"+apartment.getName());
                        remoteViews.setTextViewText(R.id.titleW, apartment.getName());
                        remoteViews.setTextViewText(R.id.numberApartmentW, String.valueOf(apartment.getId()));
                        remoteViews.setTextViewText(R.id.typeW, apartment.getApType());
                        remoteViews.setTextViewText(R.id.descriptionW, apartment.getDescription());
                        /*txtValidateR[0].setText("Nombre: "+user.getName());
                        txtValidateR[1].setText("Apellido: "+user.getLastName());
                        txtValidateR[2].setText("E-mail: "+user.getEmail());
                        txtValidateR[3].setText("Género: "+user.getGender());
                        txtValidateR[4].setText("Fecha de nacimiento: "+user.getDate());
                        txtValidateR[5].setText("Teléfono: "+user.getPhone());
                        txtValidateR[6].setText("Dirección: "+user.getAddress());
                        txtValidateR[7].setText("Ciudad: "+user.getCity());*/

                        /*Intent intent = new Intent(context1, MyWidgetProvider.class);

                        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds1);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context1,
                                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);*/
                        appWidgetManager1.updateAppWidget(id, remoteViews);

                    }else{
                        System.out.println("Respuesta post no exitosa");
                        System.out.println(response.message());
                    }
                }
                @Override
                public void onFailure(Call<Apartment> call, Throwable t) {
                    call.cancel();
                }
            });


            // Set the text
            //remoteViews.setTextViewText(R.id.update, String.valueOf(number));

            // Register an onClickListener
            /*Intent intent = new Intent(context, MyWidgetProvider.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);*/
            //appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}