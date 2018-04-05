package bathung.com.btvn_5.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import bathung.com.btvn_5.ItemClickListener;
import bathung.com.btvn_5.R;
import bathung.com.btvn_5.Task;
import io.realm.Realm;

/**
 * Created by HH on 4/4/2018.
 */

public class DoneListAdapter extends RecyclerView.Adapter<DoneListAdapter.RecyclerViewHolder>{
    private Context context;
    private List<Task> taskList;
    private static  final String LOWPRIORITY ="#2ECC71";
    private static  final String NORMALPRIORITY ="#ff5000";
    private static  final String HIGHPRIORITY ="#ff0065";
    public DoneListAdapter(List<Task> tasks, Context context){
            this.taskList = tasks;
            this.context=context;

        }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.todo_list_item,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        String priority = taskList.get(position).getPriority();
        if(priority.equals("low")){
            holder.priority.setTextColor(Color.parseColor(LOWPRIORITY));
        }else {if(priority.equals("normal")){
            holder.priority.setTextColor(Color.parseColor(NORMALPRIORITY));
        }else {
            holder.priority.setTextColor(Color.parseColor(HIGHPRIORITY));
        }
        }
        holder.priority.setText(priority);
        holder.taskName.setText(taskList.get(position).getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(taskList.get(position).getDueTo());
        holder.dueTo.setText("Due to "+date);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
               if(isLongClick) {
                   showDeleteDialog(position);
               }
            }
        });
    }
    public void showDeleteDialog(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        // Setting Dialog Title
        alertDialog.setTitle("Alert!");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this task?");
        // Setting Positive “Yes” Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "DELETE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteTask(position);
                    }
                });

        // Setting Negative “Cancel” Button
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
    private void DeleteTask(int position) {
        Realm realm = Realm.getDefaultInstance();
        final Task getTask = realm.where(Task.class).equalTo("Name",taskList.get(position).getName()).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                getTask.deleteFromRealm();
            }
        });
        taskList.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView taskName,dueTo,priority;
        private ItemClickListener itemClickListener;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            taskName = itemView.findViewById(R.id.list_item_task_name);
            dueTo = itemView.findViewById(R.id.list_item_due_to);
            priority=itemView.findViewById(R.id.list_item_priority);
        }
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),true);
            return true;
        }
    }
}
