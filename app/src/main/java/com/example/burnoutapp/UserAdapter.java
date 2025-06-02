package com.example.burnoutapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final List<UserModel> userList;
    private final Context context;

    public UserAdapter(List<UserModel> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_row, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel user = userList.get(position);
        holder.name.setText(user.getName());

        holder.iconsContainer.removeAllViews();
        holder.naView.setVisibility(View.GONE);

        Integer level = user.getBurnoutLevel();

        if (level == null || level < 1 || level > 3) {
            holder.naView.setVisibility(View.VISIBLE);
        } else {
            for (int i = 1; i <= 3; i++) {
                ImageView icon = new ImageView(context);
                int iconRes = i <= level ? R.drawable.ic_fire_on : R.drawable.ic_fire_off;
                icon.setImageResource(iconRes);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(32, 32);
                params.setMargins(4, 0, 4, 0);
                icon.setLayoutParams(params);

                holder.iconsContainer.addView(icon);
            }
        }

        holder.detailButton.setOnClickListener(v -> {
            StringBuilder detailMsg = new StringBuilder();

            detailMsg.append("ชื่อ: ").append(user.getName() != null ? user.getName() : "N/A");
            detailMsg.append("\nวันเกิด: ").append(user.getBirthday() != null ? user.getBirthday() : "N/A");
            detailMsg.append("\nเพศ: ").append(user.getGender() != null ? user.getGender() : "N/A");

            detailMsg.append("\n\nระดับ Burnout: ").append(level != null ? level : "N/A");

            Map<String, Object> advancedTest = user.getAdvancedTest();
            if (advancedTest != null) {
                detailMsg.append("\n\n[ผลแบบทดสอบขั้นสูง]");
                detailMsg.append("\nอารมณ์เหนื่อยล้า: ").append(advancedTest.get("emoExhaust"));
                detailMsg.append("\nลดความเป็นบุคคล: ").append(advancedTest.get("dePerson"));
                detailMsg.append("\nความสำเร็จส่วนตัว: ").append(advancedTest.get("personalAch"));
                detailMsg.append("\nรวม: ").append(advancedTest.get("result"));
            }

            new AlertDialog.Builder(context)
                    .setTitle("ข้อมูลผู้ใช้โดยละเอียด")
                    .setMessage(detailMsg.toString())
                    .setPositiveButton("ปิด", null)
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name, naView;
        LinearLayout iconsContainer;
        Button detailButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_name);
            iconsContainer = itemView.findViewById(R.id.burnout_icons_container);
            naView = itemView.findViewById(R.id.burnout_na);
            detailButton = itemView.findViewById(R.id.detail_button);
        }
    }
}