package com.sanoob.trilateral.adapter;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanoob.trilateral.MainActivity;
import com.sanoob.trilateral.R;
import com.sanoob.trilateral.operation.AreaCalculator;
import com.sanoob.trilateral.operation.TriangleSides;
import com.sanoob.trilateral.operation.TrianglesModel;
import java.util.List;

import static com.sanoob.trilateral.util.TrilateralUtil.cleanDoubleAndConvertToString;

// Extends the Adapter class to RecyclerView.Adapter
// and implement the unimplemented methods
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<TrianglesModel> triangles;
    Context context;

    // Constructor for initialization
    public Adapter(Context context, List<TrianglesModel> triangles) {
        this.context = context;
        this.triangles = triangles;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout(Instantiates list_item.xml
        // layout file into View object)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        // Passing view to ViewHolder
        Adapter.ViewHolder viewHolder = new Adapter.ViewHolder(view);
        return viewHolder;
    }

    // Binding data to the into specified position
    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        TrianglesModel triangle = triangles.get(position);
        holder.counter.setText(""+(position+1)+"");
        holder.sideA.setText(cleanDoubleAndConvertToString(triangle.getSides().getSideA()));
        holder.sideB.setText(cleanDoubleAndConvertToString(triangle.getSides().getSideB()));
        holder.sideC.setText(cleanDoubleAndConvertToString(triangle.getSides().getSideC()));
        holder.area.setText(cleanDoubleAndConvertToString(triangle.getArea()));
        holder.cent.setText(cleanDoubleAndConvertToString(AreaCalculator.totalAreaInCents(triangle.getArea())));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showModifyPopUp(v, triangle, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return triangles.size();
    }

    // Initializing the Views
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView sideA, sideB, sideC, area, cent, counter;
        LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
           sideA = (TextView)view.findViewById(R.id.sideATV);
           sideB = (TextView)view.findViewById(R.id.sideBTV);
           sideC = (TextView)view.findViewById(R.id.sideCTV);
           area = (TextView)view.findViewById(R.id.areaTV);
           cent = (TextView)view.findViewById(R.id.centsTV);
           counter =(TextView)view.findViewById(R.id.counterTV);
           linearLayout = (LinearLayout)view.findViewById(R.id.linear1);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private void showModifyPopUp(View view, TrianglesModel triangle, int position){

        Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.alert_layout);
        EditText sideAModify = dialog.findViewById(R.id.sideAModify);
        EditText sideBModify = dialog.findViewById(R.id.sideBModify);
        EditText sideCModify = dialog.findViewById(R.id.sideCModify);
        ImageButton saveButton = (ImageButton) dialog.findViewById(R.id.alertSave);
        ImageButton deleteButton = (ImageButton) dialog.findViewById(R.id.alertDelete);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TriangleSides sides = new TriangleSides(Double.parseDouble(sideAModify.getText().toString()),
                        Double.parseDouble(sideBModify.getText().toString()),
                        Double.parseDouble(sideCModify.getText().toString()));
                TrianglesModel triangleNew = new TrianglesModel();
                triangleNew.setSides(sides);
                double area = AreaCalculator.calculate(sides);
                if(Double.isNaN(area)){
                    Toast.makeText(dialog.getContext(), "Not a valid triangle. Please verify the input values", Toast.LENGTH_LONG).show();
                    return;
                }

                triangleNew.setArea(area);

                triangles.remove(position);
                triangles.add(position, triangleNew);
                notifyDataSetChanged();
                dialog.dismiss();

            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triangles.remove(position);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        sideAModify.setText(cleanDoubleAndConvertToString(triangle.getSides().getSideA()));
        sideBModify.setText(cleanDoubleAndConvertToString(triangle.getSides().getSideB()));
        sideCModify.setText(cleanDoubleAndConvertToString(triangle.getSides().getSideC()));
        dialog.show();

    }
}
