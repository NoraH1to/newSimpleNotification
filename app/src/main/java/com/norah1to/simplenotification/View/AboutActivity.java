package com.norah1to.simplenotification.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textview.MaterialTextView;
import com.norah1to.simplenotification.R;
import com.norah1to.simplenotification.Util.StringUtil;

public class AboutActivity extends BaseActivity {

    // 点击下载的 textview
    private MaterialTextView downloadTextView;

    // 点击跳转到 GitHub 项目地址的 textview
    private MaterialTextView githubParseTextView;

    // 客户端作者跳转
    private MaterialTextView CAuthorTextView;

    // 服务端作者跳转
    private MaterialTextView SAuthorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        downloadTextView = (MaterialTextView)findViewById(R.id.text_about_coolapk_content);
        githubParseTextView = (MaterialTextView)findViewById(R.id.text_about_github_content);
        CAuthorTextView = (MaterialTextView)findViewById(R.id.text_about_author_content);
        SAuthorTextView = (MaterialTextView)findViewById(R.id.text_about_author2_content);

        setUrlStyle(downloadTextView, getString(R.string.address_download_coolapk));
        setUrlStyle(githubParseTextView, getString(R.string.address_github_project));
        setUrlStyle(CAuthorTextView, getString(R.string.address_author_norah1to));
        setUrlStyle(SAuthorTextView, getString(R.string.address_author_wegfan));
    }

    // 设置 Url 样式和点击监听
    private void setUrlStyle(TextView textView, String uri) {
        textView.setText(StringUtil.setUrlParseStyle(textView.getText().toString()));
        textView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        });
    }
}
