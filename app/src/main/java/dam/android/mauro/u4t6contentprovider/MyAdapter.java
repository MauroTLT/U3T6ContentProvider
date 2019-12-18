package dam.android.mauro.u4t6contentprovider;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private MyContacts myContacts;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(TextView view) {
            super(view);
            this.textView = view;
        }

        public void bind(String contactData) {
            this.textView.setText(contactData);
        }
    }

    MyAdapter(MyContacts myContacts) {
        this.myContacts = myContacts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView tv = (TextView) LayoutInflater.from(parent.getContext())
                                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new MyViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(myContacts.getContactData(position));
    }

    @Override
    public int getItemCount() {
        return myContacts.getCount();
    }


}
