
package com.yyf.happyfish.wechat.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yyf.happyfish.R;
import com.yyf.happyfish.transform.GlideCircleTransform;
import com.yyf.happyfish.wechat.model.ListEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/3/18.
 */

public class WeChatAdapter extends BaseQuickAdapter<ListEntity> {

    public WeChatAdapter(Context context, List<ListEntity> datas) {
        super(context, R.layout.wechat_item, datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListEntity item) {
        helper.setText(R.id.title_tv, item.getTitle())
        .setText(R.id.source_tv,"来自"+item.getSource())
        .setImageUrl(R.id.img_firstimg,item.getFirstImg(),R.mipmap.ic_launcher,new GlideCircleTransform(mContext));
              /*  .setText(R.id.tv_miaoshu, item.getAvatar())
                .setText(R.id.tv_time, item.getName())
                .setImageUrl(R.id.img_logo, item.getAvatar(), R.mipmap.ic_launcher, new GlideCircleTransform(mContext))
                .linkify(R.id.tv_gsname);*/
    }

}


