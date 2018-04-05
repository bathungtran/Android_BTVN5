package bathung.com.btvn_5.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import bathung.com.btvn_5.R;
import bathung.com.btvn_5.Task;
import io.realm.Realm;

/**
 * Created by HH on 4/3/2018.
 */

public class TaskDialogFragment extends DialogFragment{
    public EditText taskName;
    public DatePicker dueTo;
    public RadioButton Low, High, Normal;
    public DataProcess dataProcess;
    public String oldName="";
    public TaskDialogFragment(){

    }
    public interface DataProcess{
          void AddData(Task task);
          void UpdateData(Task task, int pos);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_task_layout, null);
        taskName = view.findViewById(R.id.edtTaskName);
        dueTo = view.findViewById(R.id.tpkDueTo);
        Low = view.findViewById(R.id.rbLow);
        Normal = view.findViewById(R.id.rbNormal);
        Normal.setChecked(true);
        High = view.findViewById(R.id.rbHigh);
        final Bundle bundle = getArguments();
        if(bundle !=null){
            Task task = (Task)bundle.getSerializable("task");
            oldName=task.getName();
            taskName.setText(task.getName());
            //Set date for date picker
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(task.getDueTo());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            dueTo.updateDate(year,month,day);

            if(task.getPriority().equals("low")){
                Low.setChecked(true);
            }else {
                if(task.getPriority().equals("normal")){
                    Normal.setChecked(true);
                }else {
                    High.setChecked(true);
                }
            }
        }
        builder.setView(view);
        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    final String priority;
                    if(High.isChecked()) {
                        priority="high";
                    }
                    else{
                        if(Normal.isChecked())
                        {
                            priority="normal";
                        }
                        else
                            {priority="low";}
                    }

                    final String name = taskName.getText().toString();
                    if(!TextUtils.isEmpty(name)) {
                        final Task task = new Task(name, getDateFromDatePicker(dueTo), priority, false);
                        Realm realm = Realm.getDefaultInstance();

                        if(bundle==null){
                            final Task getTask = realm.where(Task.class).equalTo("Name",name).findFirst();
                            if(getTask==null) {
                                dataProcess.AddData(task);
                                realm.beginTransaction();
                                realm.copyToRealm(task);
                                realm.commitTransaction();
                            }else{
                                Toast.makeText(getContext(),"Task name has already exist!",Toast.LENGTH_SHORT).show();
                            }
                            //Add
                        }else {
                            int pos =bundle.getInt("position");
                            final Task getTask = realm.where(Task.class).equalTo("Name",oldName).findFirst();
                            if(getTask!=null){
                                realm.beginTransaction();
                                getTask.deleteFromRealm();
                                realm.copyToRealm(task);
                                realm.commitTransaction();
                                dataProcess.UpdateData(task,pos);
                            }else {
                                Log.e("Error","Get task null");
                            }
                        }
                    }
                }
            });
            builder.setNegativeButton("CANCEL",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            getDialog().dismiss();
                        }
                    }
            );
            return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dataProcess =(DataProcess) getTargetFragment();
        }catch (ClassCastException e){
            Log.e("Error",e.toString());
        }
    }
    public static Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }



}
