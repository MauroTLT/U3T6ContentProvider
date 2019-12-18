package dam.android.mauro.u4t6contentprovider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MyContacts {
    private ArrayList<ContactItem> myDataSet;
    private Context context;

    public MyContacts(Context context) {
        this.context = context;
        this.myDataSet = getContacts();
    }

    public void refreshData() {
        this.myDataSet = getContacts();
    }

    private ArrayList<ContactItem> getContacts() {
        ArrayList<ContactItem> contactsList = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = new String[] {
            ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI,

            ContactsContract.Data.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY,
            ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.TYPE
        };

        String selectionFilter = ContactsContract.Data.MIMETYPE + "='" +
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' AND " +
                ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL";

        Cursor contactsCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                projection,
                selectionFilter,
                null,
                ContactsContract.Data.DISPLAY_NAME + " ASC");

        if (contactsCursor != null) {
            int idIndex = contactsCursor.getColumnIndexOrThrow(ContactsContract.Data._ID);
            int nameIndex = contactsCursor.getColumnIndexOrThrow(ContactsContract.Data.DISPLAY_NAME);
            int numberIndex = contactsCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int imageIndex = contactsCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_URI);

            int contactIdIndex = contactsCursor.getColumnIndexOrThrow(ContactsContract.Data.CONTACT_ID);
            int lookupIndex = contactsCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY);
            int rawContactIdIndex = contactsCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID);
            int phoneType = contactsCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.TYPE);

            while (contactsCursor.moveToNext()) {
                int id = contactsCursor.getInt(idIndex);
                String uri = contactsCursor.getString(imageIndex);
                String phoneTypeName = ContactsContract.CommonDataKinds.Phone.getTypeLabel(
                                context.getResources(), contactsCursor.getInt(phoneType), "Mobile").toString();
                contactsList.add(new ContactItem(
                        id,
                        contactsCursor.getString(nameIndex),
                        contactsCursor.getString(numberIndex),
                        (uri != null)? Uri.parse(uri) : null,
                        contactsCursor.getInt(contactIdIndex),
                        contactsCursor.getString(lookupIndex),
                        contactsCursor.getString(rawContactIdIndex),
                        // TODO Ex2-1 Check the type of the number and print the correct
                        (phoneTypeName.equalsIgnoreCase("Home") ||
                         phoneTypeName.equalsIgnoreCase("Work") ||
                         phoneTypeName.equalsIgnoreCase("Mobile"))? phoneTypeName : "Other"
                ));
            }
            contactsCursor.close();
        }
        return contactsList;
    }

    public ContactItem getContactData(int position) {
        return myDataSet.get(position);
    }

    public int getCount() {
        return myDataSet.size();
    }
}
