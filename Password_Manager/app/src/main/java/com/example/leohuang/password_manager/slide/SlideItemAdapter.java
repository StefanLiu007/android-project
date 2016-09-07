package com.example.leohuang.password_manager.slide;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.db.DatabaseManager;

import java.util.List;

/**
 * Created by yjwfn on 15-11-28.
 */
public class SlideItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemSlideHelper.Callback {

    private RecyclerView mRecyclerView;
    List<Item> list;
    Context context;
    private MyApplication myApplication;
//    public SlideItemAdapter(List<Item> list, Context context) {
//        this.list = list;
//        this.context = context;
//    }

    public SlideItemAdapter(List<Item> list, Context context, MyApplication myApplication) {
        this.list = list;
        this.context = context;
        this.myApplication = myApplication;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item, parent, false);
        return new TextVH(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // String text = "item: " + position;
        final TextVH textVH = (TextVH) holder;
        textVH.textView.setText(list.get(position).name);
        if (MyApplication.icons.get(list.get(position).icon) != null) {
            textVH.acc_icon.setBackgroundResource(MyApplication.icons.get(list.get(position).icon));
        } else {
            textVH.acc_icon.setBackgroundResource(R.mipmap.ic_launcher);
        }
        final Item item = list.get(holder.getAdapterPosition());
        //  textVH.acc_drag.setBackgroundResource(R.drawable.ic_more_vert_grey_24dp);
        textVH.iv_delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new AlertDialog.Builder(context)
                        .setTitle("确认")
                        .setMessage("永久删除该账户？")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseManager.deleteItem(list.get(position));
                                list.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                            }

                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }

                        }).create();
                dialog.show();
            }
        });
//        textVH.iv_collection1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (item.is_favorited == 1) {
//                    textVH.iv_collection1.setImageResource(R.drawable.collection);
//                    myApplication.mExecutor.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            DatabaseManager.addOrDeleteFavroite(item.guid, 0);
//                        }
//                    });
//                } else {
//                    textVH.iv_collection1.setImageResource(R.drawable.no_collection);
//                    myApplication.mExecutor.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            DatabaseManager.addOrDeleteFavroite(item.guid, 1);
//                        }
//                    });
//                }
//            }
//        });
        if (item.is_favorited == 1) {
            textVH.iv_collection1.setChecked(true);
        } else {
            textVH.iv_collection1.setChecked(false);
        }
        textVH.iv_collection1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myApplication.mExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            DatabaseManager.addOrDeleteFavroite(item.guid, 1);
                        }
                    });
                } else {
                    myApplication.mExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            DatabaseManager.addOrDeleteFavroite(item.guid, 0);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        mRecyclerView.addOnItemTouchListener(new ItemSlideHelper(mRecyclerView.getContext(), this));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {

        if (holder.itemView instanceof LinearLayout) {
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            if (viewGroup.getChildCount() == 2) {
                return viewGroup.getChildAt(1).getLayoutParams().width;
            }
        }

        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return mRecyclerView.getChildViewHolder(childView);
    }

    @Override
    public View findTargetView(float x, float y) {
        return mRecyclerView.findChildViewUnder(x, y);
    }

}

class TextVH extends RecyclerView.ViewHolder {

    TextView textView;
    ImageView acc_icon;
    ImageView iv_delete1;
    ToggleButton iv_collection1;

    public TextVH(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.acc_name);
        acc_icon = (ImageView) itemView.findViewById(R.id.acc_icon);
        // acc_drag = (ImageView) itemView.findViewById(R.id.acc_drag);
        iv_delete1 = (ImageView) itemView.findViewById(R.id.iv_delete1);
        iv_collection1 = (ToggleButton) itemView.findViewById(R.id.iv_collection1);
    }
}