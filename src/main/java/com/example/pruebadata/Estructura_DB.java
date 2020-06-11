package com.example.pruebadata;

public class Estructura_DB {

    private Estructura_DB() {}

        public static final String NOMBRE_TABLA = "datosPersonales";
        public static final String NOMBRE_COLUMNA1 = "ID";
        public static final String NOMBRE_COLUMNA2 = "NOMBRE";
        public static final String NOMBRE_COLUMNA3 = "APELLIDO";

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Estructura_DB.NOMBRE_TABLA + " (" +
                    Estructura_DB.NOMBRE_COLUMNA1 + " INTEGER PRIMARY KEY," +
                    Estructura_DB.NOMBRE_COLUMNA2 + " TEXT," +
                    Estructura_DB.NOMBRE_COLUMNA3 + " TEXT)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Estructura_DB.NOMBRE_TABLA;

}
