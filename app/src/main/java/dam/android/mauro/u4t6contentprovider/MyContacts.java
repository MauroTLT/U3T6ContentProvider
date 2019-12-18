package dam.android.mauro.u4t6contentprovider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class MyContacts {
    private ArrayList<String> myDataSet;
    private Context context;

    public MyContacts(Context context) {
        this.context = context;
        this.myDataSet = getContacts();
    }

    private ArrayList<String> getContacts() {
        ArrayList<String> contactsList = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = new String[] {
            ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
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
            int nameIndex = contactsCursor.getColumnIndexOrThrow(ContactsContract.Data.DISPLAY_NAME);
            int numbreIndex = contactsCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);

            while (contactsCursor.moveToNext()) {
                String name = contactsCursor.getString(nameIndex);
                String number = contactsCursor.getString(numbreIndex);
                contactsList.add(name + ": " + number);
            }
            contactsCursor.close();
        }
        return contactsList;
    }

    public String getContactData(int position) {
        return myDataSet.get(position);
    }

    public int getCount() {
        return myDataSet.size();
    }
}
