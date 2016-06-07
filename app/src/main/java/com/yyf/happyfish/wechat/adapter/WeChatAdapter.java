
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
        /**
         * 为item设置不同的背景 要使用R.layout.wechat_item_threebg 这个布局
         * */
        /*helper.setBackgroundRes(R.id.item1, R.mipmap.hongbao1).setText(R.id.title_tv, item.getTitle()) .setText(R.id.source_tv, "来自" + item.getSource())
                .setImageUrl(R.id.img_firstimg, item.getFirstImg(), R.mipmap.ic_launcher, new GlideCircleTransform(mContext));
        helper.setBackgroundRes(R.id.item2, R.mipmap.hoongbao2).setText(R.id.title_tv1, item.getTitle())
                .setText(R.id.source_tv1, "来自" + item.getSource())
                .setImageUrl(R.id.img_firstimg1, item.getFirstImg(), R.mipmap.ic_launcher, new GlideCircleTransform(mContext));
        helper.setBackgroundRes(R.id.item3, R.mipmap.hongbao3).setText(R.id.title_tv2, item.getTitle())
                .setText(R.id.source_tv2, "来自" + item.getSource())
                .setImageUrl(R.id.img_firstimg2, item.getFirstImg(), R.mipmap.ic_launcher, new GlideCircleTransform(mContext));
*/

        /**
         * 不使用不同背景  要使用R.layout.wechat_item这个布局
         * */
        helper.setText(R.id.title_tv, item.getTitle())
                .setText(R.id.source_tv, "来自" + item.getSource())
                .setImageUrl(R.id.img_firstimg, item.getFirstImg(), R.mipmap.ic_launcher, new GlideCircleTransform(mContext));
              /*  .setText(R.id.tv_miaoshu, item.getAvatar())
                .setText(R.id.tv_time, item.getName())
                .setImageUrl(R.id.img_logo, item.getAvatar(), R.mipmap.ic_launcher, new GlideCircleTransform(mContext))
                .linkify(R.id.tv_gsname);*/
    }

}


