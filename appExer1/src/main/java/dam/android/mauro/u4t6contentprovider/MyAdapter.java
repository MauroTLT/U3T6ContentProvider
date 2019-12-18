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

    private MyContacts myContacts;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        // TODO Ex1-2 The contactItem now has all this views to display info
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

        // TODO Ex1-2 Ex1-3 Bind the values of the ContactItem to the views
        public void bind(ContactItem contact) {
            this.tvId.setText(String.valueOf(contact.getId()));
            this.tvName.setText(contact.getNombre());
            this.tvNumber.setText(String.valueOf(contact.getTelefono()));
            if (contact.getImagen() != null) {
                this.imageView.setImageURI(contact.getImagen());
            } else {
                this.imageView.setBackgroundResource(R.mipmap.ic_launcher);
            }
        }
    }

    MyAdapter(MyContacts myContacts) {
        this.myContacts = myContacts;
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
        holder.bind(myContacts.getContactData(position));
    }

    @Override
    public int getItemCount() {
        return myContacts.getCount();
    }


}
