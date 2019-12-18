package dam.android.mauro.u4t6contentprovider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener, MyAdapter.OnItemLongClickListener {

    private final int REQUEST = 0;

    MyContacts myContacts;
    RecyclerView recyclerView;
    // TODO Ex2-1 View to display the data of the contact clicked
    TextView contactDetails;

    private static String[] PERMISSIONS_CONTACTS = {Manifest.permission.READ_CONTACTS};

    private static final int REQUEST_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        setUI();

        if (checkPermissions())
            setListAdapter();
    }

    private void setUI() {
        contactDetails = findViewById(R.id.contactDetails);
        recyclerView = findViewById(R.id.recyclerViewContacts);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        recyclerView.addItemDecoration(
            new DividerItemDecoration(
                recyclerView.getContext(),
                ((LinearLayoutManager)recyclerView.getLayoutManager()).getOrientation()
            )
         );
    }

    private void setListAdapter() {
        myContacts = new MyContacts(this);
        // TODO Ex2-1 Pass the listeners to the adapter
        recyclerView.setAdapter(new MyAdapter(myContacts, this, this));
        // TODO Ex2-1 When scroll the recyclerView make invisible the TextView
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                contactDetails.setVisibility(View.INVISIBLE);
            }
        });

        if (myContacts.getCount() > 0) findViewById(R.id.tvEmptyList).setVisibility(View.INVISIBLE);
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, MainActivity.PERMISSIONS_CONTACTS, MainActivity.REQUEST_CONTACTS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setListAdapter();
            } else {
                Toast.makeText(this, getString(R.string.contacts_read_right_required), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // TODO Ex2-1 Listen when clicked in a contact, show the data
    @Override
    public void onItemClick(ContactItem item) {
        contactDetails.setText(item.toString());
        contactDetails.setVisibility(View.VISIBLE);
    }

    // TODO Ex2-2 Listen when long click in a contact, show the contact from the Contact App
    @Override
    public boolean onItemLongClick(ContactItem item) {
        Toast.makeText(this, item.getNombre(), Toast.LENGTH_LONG).show();
        Uri uri = ContactsContract.Contacts.getLookupUri(item.getId(), item.getLookUpKey());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivityForResult(intent, REQUEST);
        return true;
    }

    // TODO Ex2-2 Wait for a response form the Contact App to refresh data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST) {
            if (resultCode >= 0) {
                ((MyAdapter)recyclerView.getAdapter()).refreshData();
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }
}
