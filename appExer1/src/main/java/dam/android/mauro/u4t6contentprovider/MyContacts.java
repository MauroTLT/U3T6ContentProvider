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

    private ArrayList<ContactItem> getContacts() {
        ArrayList<ContactItem> contactsList = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();
        // TODO Ex1-1 Set in the projection all the values we want from the contact
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
            // TODO Ex1-1 Get the index of the new values
            int contactIdIndex = contactsCursor.getColumnIndexOrThrow(ContactsContract.Data.CONTACT_ID);
            int lookupIndex = contactsCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY);
            int rawContactIdIndex = contactsCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID);
            int phoneType = contactsCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.TYPE);

            while (contactsCursor.moveToNext()) {
                int id = contactsCursor.getInt(idIndex);
                String uri = contactsCursor.getString(imageIndex);
                String phoneTypeName = ContactsContract.CommonDataKinds.Phone.getTypeLabel(
                        context.getResources(), contactsCursor.getInt(phoneType), "Mobile").toString();
                // TODO Ex1-1 Construct the ContactItem with the values received
                contactsList.add(new ContactItem(
                        id,
                        contactsCursor.getString(nameIndex),
                        contactsCursor.getString(numberIndex),
                        (uri != null)? Uri.parse(uri) : null,
                        contactsCursor.getInt(contactIdIndex),
                        contactsCursor.getString(lookupIndex),
                        contactsCursor.getString(rawContactIdIndex),
                        phoneTypeName
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
