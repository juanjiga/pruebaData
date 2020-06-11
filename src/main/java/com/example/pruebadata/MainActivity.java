package com.example.pruebadata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button boton_create, boton_read, boton_update, boton_delete;
    EditText texto_id, texto_nombre, texto_apellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton_create = (Button) findViewById(R.id.create);
        boton_read = (Button) findViewById(R.id.read);
        boton_update = (Button) findViewById(R.id.update);
        boton_delete = (Button) findViewById(R.id.delete);
        texto_id = (EditText) findViewById(R.id.id);
        texto_nombre = (EditText) findViewById(R.id.nombre);
        texto_apellido = (EditText) findViewById(R.id.apellido);

        final Helper_DB helper = new Helper_DB(this);

        boton_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gets the data repository in write mode
                SQLiteDatabase db = helper.getWritableDatabase();
                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(Estructura_DB.NOMBRE_COLUMNA1, texto_id.getText().toString());
                values.put(Estructura_DB.NOMBRE_COLUMNA2, texto_nombre.getText().toString());
                values.put(Estructura_DB.NOMBRE_COLUMNA3, texto_apellido.getText().toString());
                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(Estructura_DB.NOMBRE_TABLA, null, values);
                Toast.makeText(getApplicationContext(), "Se guardó el registro con id: " +
                        newRowId, Toast.LENGTH_LONG).show();
            }
        });

        boton_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getReadableDatabase();
                // Define a projection that specifies which columns from the database
                // you will actually use after this query.
                String[] projection = {
                        //Estructura_DB.NOMBRE_COLUMNA1,
                        Estructura_DB.NOMBRE_COLUMNA2,
                        Estructura_DB.NOMBRE_COLUMNA3,
                };
                // Filter results WHERE "title" = 'My Title'
                String selection = Estructura_DB.NOMBRE_COLUMNA1 + " = ?";
                String[] selectionArgs = { texto_id.getText().toString() };
                // How you want the results sorted in the resulting Cursor
                /*String sortOrder =
                        FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";*/
                try {
                    Cursor cursor = db.query(
                            Estructura_DB.NOMBRE_TABLA,   // The table to query
                            projection,             // The array of columns to return (pass null to get all)
                            selection,              // The columns for the WHERE clause
                            selectionArgs,          // The values for the WHERE clause
                            null,                   // don't group the rows
                            null,                   // don't filter by row groups
                            null            //sortOrder               // The sort order
                    );
                    cursor.moveToFirst();
                    //muestra el resultado de la busqueda
                    texto_nombre.setText(cursor.getString(0));
                    texto_apellido.setText(cursor.getString(1));
                }catch (Exception e){
                    mensaje("No se encontró el registro con id: ");
                    borraTextos();
                }
            }
        });

        boton_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // abre la base de datos en modo escritura
                SQLiteDatabase db = helper.getWritableDatabase();

                // New value for one column
                String title = "MyNewTitle";
                ContentValues values = new ContentValues();
                values.put(Estructura_DB.NOMBRE_COLUMNA2, texto_nombre.getText().toString());
                values.put(Estructura_DB.NOMBRE_COLUMNA3, texto_apellido.getText().toString());
                // Which row to update, based on the title //criterio, el id no se modifica
                String selection = Estructura_DB.NOMBRE_COLUMNA1 + " LIKE ?";
                String[] selectionArgs = { texto_id.getText().toString() };

                int count = db.update(
                        Estructura_DB.NOMBRE_TABLA,
                        values,
                        selection,
                        selectionArgs);
                mensaje("El registro ha sido actualizado ");

            }
        });

        boton_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                // Define 'where' part of query.
                String selection = Estructura_DB.NOMBRE_COLUMNA1 + " LIKE ?";
                // Specify arguments in placeholder order.
                String[] selectionArgs = { texto_id.getText().toString() };
                // Issue SQL statement.
                int deletedRows = db.delete(Estructura_DB.NOMBRE_TABLA, selection, selectionArgs);



                mensaje("Se ha borrado el registro con id: ");
                borraTextos();



            }
        });

    }
    public void borraTextos(){
        texto_id.setText("");
        texto_nombre.setText("");
        texto_apellido.setText("");
    }
    public void mensaje(String mensajito){
        Toast.makeText(getApplicationContext(), mensajito + texto_id.getText().toString(),
                Toast.LENGTH_LONG).show();
    }
}
