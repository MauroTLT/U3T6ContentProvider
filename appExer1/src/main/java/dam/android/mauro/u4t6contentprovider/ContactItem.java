package dam.android.mauro.u4t6contentprovider;

import android.net.Uri;

// TODO Ex1-3 Create a class for the contact
public class ContactItem {

    private int id;
    private String nombre;
    private String telefono;
    private Uri imagen;

    private int contactId;
    private String lookUpKey;
    private String rawContactId;
    private String phoneType;

    public ContactItem(int id, String nombre, String telefono, Uri imagen, int contactId, String lookUpKey, String rawContactId, String phoneType) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.imagen = imagen;

        this.contactId = contactId;
        this.lookUpKey = lookUpKey;
        this.rawContactId = rawContactId;
        this.phoneType = phoneType;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public Uri getImagen() {
        return imagen;
    }

    public int getContactId() {
        return contactId;
    }

    public String getLookUpKey() {
        return lookUpKey;
    }

    public String getRawContactId() {
        return rawContactId;
    }

    public String getPhoneType() {
        return phoneType;
    }
}
