package com.example.sns_project.listener;

import com.example.sns_project.PostInfo;

public interface OnPostListener {
    void onDelete(PostInfo postInfo);
    void onModify();
}
