package com.example.everardo.xml_parsing;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class MainActivity extends ListActivity {

    /// Se decalran todas las variables estáticas y la direccion que obtiene todos los datos del archivo XML
    static final String URL = "http://eulisesrdz.260mb.net/eve/bbdd.xml";
    //  todos los nodos de Xml
    static final String KEY_REGISTRO = "registro"; // Nodo Padre
    static final String KEY_ID = "id_persona";
    static final String KEY_NOMBRE = "nombre";
    static final String KEY_APELLIDOS = "apellidos";
    static final String KEY_TIPO_EXAMEN = "tipo_examen";
    static final String KEY_NIVEL = "nivel";
    static final String KEY_FECHA = "fecha";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

        XMLParser parser = new XMLParser();
        String xml = parser.getXmlFromUrl(URL); // Conseguir Xml
        Document doc = parser.getDomElement(xml); // conseguir elemento DOM
        NodeList nl = doc.getElementsByTagName(KEY_REGISTRO);
        // bucle a través de todos los nodos de elementos <item>
        for (int i = 0; i < nl.getLength(); i++) {
            // crear nuevo HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            // añadiendo cada nodo hijo a HashMap clave => valor
            map.put(KEY_ID, " " + parser.getValue(e, KEY_ID));
            map.put(KEY_NOMBRE," " + parser.getValue(e, KEY_NOMBRE));
            map.put(KEY_APELLIDOS, " "+ parser.getValue(e, KEY_APELLIDOS));
            map.put(KEY_TIPO_EXAMEN," "+ parser.getValue(e, KEY_TIPO_EXAMEN));
            map.put(KEY_NIVEL," " + parser.getValue(e, KEY_NIVEL));
            map.put(KEY_FECHA, " " + parser.getValue(e, KEY_FECHA));

            // añadiendo lista ordenada de ArrayList
            menuItems.add(map);
        }

    // Adición MenuItems a ListView
    ListAdapter adapter = new SimpleAdapter(this, menuItems,
            R.layout.lista_usuarios,
            new String[] { KEY_ID, KEY_NOMBRE, KEY_APELLIDOS, KEY_TIPO_EXAMEN, KEY_NIVEL, KEY_FECHA}, new int[] {
            R.id.txt_l_idpersona, R.id.txt_l_nombre, R.id.txt_l_apellidos,R.id.txt_l_tipo_examen, R.id.txt_l_nivel, R.id.txt_l_fecha});

        setListAdapter(adapter);

        // seleccionar elemento ListView item
        ListView lv = getListView();

        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
                public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
                // obtener los valores de ListItem seleccionado
                String id_persona = ((TextView) view.findViewById(R.id.txt_l_idpersona)).getText().toString();
                String nombre = ((TextView) view.findViewById(R.id.txt_l_nombre)).getText().toString();
                String apellidos = ((TextView) view.findViewById(R.id.txt_l_apellidos)).getText().toString();
                String tipo_examen = ((TextView) view.findViewById(R.id.txt_l_tipo_examen)).getText().toString();
                String nivel = ((TextView) view.findViewById(R.id.txt_l_nivel)).getText().toString();
                String fecha = ((TextView) view.findViewById(R.id.txt_l_fecha)).getText().toString();

                // Comenzando nuevo intento
                Intent in = new Intent(getApplicationContext(), vista_individual.class);
                in.putExtra(KEY_ID, id_persona);
                in.putExtra(KEY_NOMBRE, nombre);
                in.putExtra(KEY_APELLIDOS,apellidos);
                in.putExtra(KEY_TIPO_EXAMEN,tipo_examen);
                in.putExtra(KEY_NIVEL,nivel);
                in.putExtra(KEY_FECHA,fecha);
                startActivity(in);

            }
        });
    }

   }

