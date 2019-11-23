package com.norah1to.simplenotification;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.norah1to.simplenotification.Settings.SharePreferencesHelper;

public class MainSheetDialogFragment extends BottomSheetDialogFragment {


    // 用户头像
    private ImageView userImage;

    // 用户账号
    private MaterialTextView userAccount;

    // 用户卡片
    private MaterialCardView userCard;

    // 设置卡片
    private MaterialCardView settingCard;

    // 关于卡片
    private MaterialCardView aboutCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_sheet, container, false);

        // 初始化用户头像组件
        userImage = (ImageView) view.findViewById(R.id.user_img_main_bottom_sheet);

        // 初始化用户账号组件
        userAccount = (MaterialTextView) view.findViewById(R.id.user_account_main_bottom_sheet);

        // 初始化用户卡片
        userCard = (MaterialCardView) view.findViewById(R.id.user_card_main_bottom_sheet);

        // 初始化设置卡片
        settingCard = (MaterialCardView) view.findViewById(R.id.settings_card_main_bottom_sheet);

        // 初始化关于卡片
        aboutCard = (MaterialCardView) view.findViewById(R.id.about_card_main_bottom_sheet);

        // 跳转用户
        userCard.setOnClickListener(v -> {
            this.dismiss();
            // 用户登入就到用户中心
            Intent intent;
            if (SharePreferencesHelper.getUserState(this.getContext()) != null) {
                intent = new Intent(this.getContext(), UserCenterActivity.class);
            } else { // 否则就登入
                intent = new Intent(this.getContext(), LoginActivity.class);
            }
            startActivity(intent);
        });

        // 跳转设置
        settingCard.setOnClickListener(v -> {
            this.dismiss();
            Intent intent = new Intent(view.getContext(), SettingsActivity.class);
            startActivity(intent);
        });

        // 跳转关于
        aboutCard.setOnClickListener(v -> {
            this.dismiss();
            Intent intent = new Intent(view.getContext(), AboutActivity.class);
            startActivity(intent);
        });

        initUserInfo();

        return view;
    }

    private void initUserInfo() {
        String account = SharePreferencesHelper.getUserState(this.getContext());
        if (account != null) {
            userAccount.setText(account);
        }
    }
}
