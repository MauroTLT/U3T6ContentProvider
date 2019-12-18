package dam.android.mauro.u4t6contentprovider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ContactItem activityName);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(ContactItem activityName);
    }

    private MyContacts myContacts;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout;
        TextView tvId, tvName, tvNumber;
        ImageView imageView;

        public MyViewHolder(ConstraintLayout view) {
            super(view);
            this.layout = view;
            this.tvId = view.findViewById(R.id.tvId);
            this.tvName = view.findViewById(R.id.tvName);
            this.tvNumber = view.findViewById(R.id.tvNumber);
            this.imageView = view.findViewById(R.id.image_view);
        }

        public void bind(final ContactItem contact, final OnItemClickListener clickListener, final OnItemLongClickListener longClickListener) {
            this.tvId.setText(String.valueOf(contact.getId()));
            this.tvName.setText(contact.getNombre());
            this.tvNumber.setText(String.valueOf(contact.getTelefono()));
            if (contact.getImagen() != null) {
                this.imageView.setImageURI(contact.getImagen());
            } else {
                this.imageView.setBackgroundResource(R.mipmap.ic_launcher);
            }
            // TODO Ex2-1 Ex2-2 Set the listeners to the items
            this.layout.setOnClickListener( v -> clickListener.onItemClick(contact));
            this.layout.setOnLongClickListener( v -> longClickListener.onItemLongClick(contact));
        }
    }

    // TODO Ex2-2 Receive the listeners of click and long click
    MyAdapter(MyContacts myContacts, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        this.myContacts = myContacts;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    // TODO Ex2-2 Refresh the data
    public void refreshData() {
        myContacts.refreshData();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.contact_item, parent, false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(myContacts.getContactData(position), clickListener, longClickListener);
    }

    @Override
    public int getItemCount() {
        return myContacts.getCount();
    }


}
