package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.ItemViewHolder> {

    private Context mctx;
    private List<forecast_item> itemslist;

    public itemAdapter(Context mctx, List<forecast_item> itemslist) {
        this.mctx = mctx;
        this.itemslist = itemslist;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view= (View) inflater.inflate(R.layout.forecast_layout,null);
        ItemViewHolder hold = new ItemViewHolder(view);
        return hold;

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.descr.setText(itemslist.get(position).getDescription());
        holder.datetime.setText(itemslist.get(position).getDatetime());
        holder.wind.setText(itemslist.get(position).getWind()+" m/s");
        holder.press.setText(itemslist.get(position).getPressure()+" hPa");
        holder.temp.setText(itemslist.get(position).getTemperature()+" C");
        holder.humid.setText(itemslist.get(position).getHumidity()+" %");
        Picasso.get().load("http://openweathermap.org/img/w/"+itemslist.get(position).getImglink()+".png").into(holder.icon_img);

    }

    @Override
    public int getItemCount() {
        return itemslist.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView descr;
        TextView datetime;
        TextView wind;
        TextView press;
        TextView temp;
        TextView humid;
        ImageView icon_img;



        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            descr = itemView.findViewById(R.id.fore_desc);
            temp = itemView.findViewById(R.id.fore_temp);
            datetime = itemView.findViewById(R.id.date_time);
            wind = itemView.findViewById(R.id.wind_data);
            humid = itemView.findViewById(R.id.hum_data);
            press = itemView.findViewById(R.id.press_data);
            icon_img = itemView.findViewById(R.id.imageView);





//            sh.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            int pos;
//            pos = getLayoutPosition();
//            if(view.getId()==R.id.button){
//                itemslist.remove(pos);
//                notifyDataSetChanged();
//            }
//            if(view.getId() == R.id.button2)
//            {
//                //Sharing
//                /*Intent intent = new Intent(mctx,Main2Activity.class);
//                intent.putExtra("name",itemslist.get(pos).getName());
//                intent.putExtra("btn_name",itemslist.get(pos).getBtn_name());
//                mctx.startActivity(intent);
//                */
//                Intent intent = new Intent(mctx,Main2Activity.class);
//                intent.putExtra("name",itemslist.get(pos).getName());
//                intent.putExtra("btn_name",itemslist.get(pos).getBtn_name());
//                mctx.startActivity(intent);
//
//
//            }
//            if(view.getId()==R.id.share) {
//                String st = itemslist.get(pos).getName();
//
//                String mime = "type/plain";
//                ShareCompat.IntentBuilder
//                        .from((Activity) mctx)
//                        .setType(mime)
//                        .setChooserTitle("Hello")
//                        .setText(st)
//                        .startChooser();
//            }
        }
    }
}
