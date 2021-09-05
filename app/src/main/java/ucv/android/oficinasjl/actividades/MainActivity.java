package ucv.android.oficinasjl.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ucv.android.oficinasjl.R;
import ucv.android.oficinasjl.adaptadores.AdapterSala;
import ucv.android.oficinasjl.modelos.Sala;
import ucv.android.oficinasjl.utilidades.Constants;
import ucv.android.oficinasjl.utilidades.PreferenceManager;

public class MainActivity extends AppCompatActivity{

    private PreferenceManager preferenceManager;
    ListView ListViewSala; // declaracion del componentes listview
    List<Sala> lst; // variable para trabajar con el adaptador de la sala

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceManager = new PreferenceManager(getApplicationContext()); // ahora preference manager se encarga de este contexto

        TextView textTittle = findViewById(R.id.textTittle); // el recurso con se nombre de id se inicializa con la variable textTittle
        textTittle.setText(String.format(                    // el formato consiste de un solo parametro que permite
            "%s",
            preferenceManager.getString(Constants.KEY_NOMBRE) // recuperar el nombre y poder establecerlo como texto
                                                              // en la variable textTittle
        ));

        findViewById(R.id.textSignOut).setOnClickListener(v -> SingOut()); // accede al recurso llamado textSignOut sobre un boton que permite
                                                                           // ejecutar el metodo oyente

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() { //documentacion
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) { // la aplicacion recibe la instancia de la tarea que espera enviar como mensaje
                if(task.isSuccessful() && task.getResult()!=null) {        // para recuperar el token actual
                    sendFCMTokenToDataBase(task.getResult().getToken());   // envia el mensaje FCM de Registro al servidor de firebase conectada a nuestra app
                }
            }
        });

        ListViewSala = findViewById(R.id.ListViewUniveridad);               // encuentra la lista de la universidad

        AdapterSala adapter = new AdapterSala(this, GetData()); // se crea la variable de tipo AdapterSala y se recuperan los datos inflados
        ListViewSala.setAdapter(adapter); //se le aumenta la personalizacion del adapter a la lista de la universidad

        ListViewSala.setOnItemClickListener(new AdapterView.OnItemClickListener() { // metodo oyente de la lista que se adaptara de la siguiente manera
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // esta lista esta compuesta por los metodos del padre que contiene la vista
                                                                                               // una posicion y ciera id
                Sala sala = lst.get(position);                                        // se utiliza a la sala como si fuera un arreglo y se pide la posicion de ese arreglo
                Toast.makeText(MainActivity.this, sala.nombre, Toast.LENGTH_SHORT).show(); // la sala ahora que es un arreglo en la determinada mostrara su nombre

                // comparacion de la sala escogida
                if (sala.id == 1){
                    EditText editText = findViewById(R.id.conferenceName);
                    editText.setText("SALA_DE_ADMISIÓN");
                    String text = editText.getText().toString();
                    if (text.length() > 0) {
                        JitsiMeetConferenceOptions options
                                = new JitsiMeetConferenceOptions.Builder()
                                .setRoom(text)
                                .build();
                        JitsiMeetActivity.launch(MainActivity.this, options);
                    }
                }
                if (sala.id == 2){
                    EditText editText = findViewById(R.id.conferenceName);
                    editText.setText("SALA_DE_POLIDEPORTIVO");
                    String text = editText.getText().toString();
                    if (text.length() > 0) {
                        JitsiMeetConferenceOptions options
                                = new JitsiMeetConferenceOptions.Builder()
                                .setRoom(text)
                                .build();
                        JitsiMeetActivity.launch(MainActivity.this, options);
                    }
                }

                if (sala.id == 3){
                    EditText editText = findViewById(R.id.conferenceName);
                    editText.setText("SALA_DE_PAGOS");
                    String text = editText.getText().toString();
                    if (text.length() > 0) {
                        JitsiMeetConferenceOptions options
                                = new JitsiMeetConferenceOptions.Builder()
                                .setRoom(text)
                                .build();
                        JitsiMeetActivity.launch(MainActivity.this, options);
                    }
                }
                if (sala.id == 4){
                    EditText editText = findViewById(R.id.conferenceName);
                    editText.setText("SALA_DE_ADMINISTRACIÓN");
                    String text = editText.getText().toString();
                    if (text.length() > 0) {
                        JitsiMeetConferenceOptions options
                                = new JitsiMeetConferenceOptions.Builder()
                                .setRoom(text)
                                .build();
                        JitsiMeetActivity.launch(MainActivity.this, options);
                    }
                }
                if (sala.id == 5){
                    EditText editText = findViewById(R.id.conferenceName);
                    editText.setText("SALA_DE_INGENIERÍA");
                    String text = editText.getText().toString();
                    if (text.length() > 0) {
                        JitsiMeetConferenceOptions options
                                = new JitsiMeetConferenceOptions.Builder()
                                .setRoom(text)
                                .build();
                        JitsiMeetActivity.launch(MainActivity.this, options);
                    }
                }
                if (sala.id == 6){
                    EditText editText = findViewById(R.id.conferenceName);
                    editText.setText("SALA_DE_QUÍMICA");
                    String text = editText.getText().toString();
                    if (text.length() > 0) {
                        JitsiMeetConferenceOptions options
                                = new JitsiMeetConferenceOptions.Builder()
                                .setRoom(text)
                                .build();
                        JitsiMeetActivity.launch(MainActivity.this, options);
                    }
                }
                /*
                if (sala.id == 1){
                    EditText editText = findViewById(R.id.conferenceName);
                    editText.setText("ENTRADA_A_PUESTO_DE_VIGILANCIA");
                    String text = editText.getText().toString();
                    if (text.length() > 0) {
                        JitsiMeetConferenceOptions options
                                = new JitsiMeetConferenceOptions.Builder()
                                .setRoom(text)
                                .build();
                        JitsiMeetActivity.launch(MainActivity.this, options);
                    }
                }
                if (sala.id == 2){
                    EditText editText = findViewById(R.id.conferenceName);
                    editText.setText("CAFETERÍA");
                    String text = editText.getText().toString();
                    String usuario = textTittle.getText().toString();
                    if (text.length() > 0) {
                        JitsiMeetConferenceOptions options
                                = new JitsiMeetConferenceOptions.Builder()
                                .setRoom(text)
                                .build();
                        JitsiMeetActivity.launch(MainActivity.this, options);
                    }
                }

                if (sala.id == 3){
                    EditText editText = findViewById(R.id.conferenceName);
                    editText.setText("OFICINA_DE_VIGILANCIA");
                    String text = editText.getText().toString();
                    if (text.length() > 0) {
                        JitsiMeetConferenceOptions options
                                = new JitsiMeetConferenceOptions.Builder()
                                .setRoom(text)
                                .build();
                        JitsiMeetActivity.launch(MainActivity.this, options);
                    }
                }*/

            }
        });
    }

// uso de tokens dinamicamente, el primero genera un token y el otro lo elimina
    private void sendFCMTokenToDataBase(String token){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
            database.collection(Constants.KEY_COLLECION_USERS).document(
                    preferenceManager.getString(Constants.KEY_USER_ID)
            );
        documentReference.update(Constants.KEY_FCM_TOKEN,token)
                //ya no necesitamos este mensaje de "token actualizado correctamente", así que elimínelo
            //.addOnCompleteListener(task -> Toast.makeText(MainActivity.this,"Token actualizado satisfactoriamente", Toast.LENGTH_SHORT).show())
            .addOnFailureListener(e -> Toast.makeText(MainActivity.this,"Token no enviado!: "+e.getMessage(), Toast.LENGTH_SHORT).show());

    }
// metodo para cerrar sesion
    private void SingOut(){
        Toast.makeText(this,"Cerrando sesión...",Toast.LENGTH_SHORT).show();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference=
            database.collection(Constants.KEY_COLLECION_USERS).document(
                    preferenceManager.getString(Constants.KEY_USER_ID)
            );
        HashMap<String, Object> updates=new HashMap<>(); // se crea un mapa de tipo hash
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete()); // el mapa va a encontrar el token y lo va a eliminar
        documentReference.update(updates) // ahora el documento que es usuario queda actualizado prescindiendo del token
                .addOnSuccessListener(unused -> {        // ya que el token no esta en uso
                    preferenceManager.clearPreference(); // ahora se limpia lo que se haya guardado en preference manager
                    startActivity(new Intent(getApplicationContext(),IniciarSesion.class)); // desde el inicio de sesion
                    finish();   // termina de buscar en el mapa
                })
                // muestra un mensaje que puede pasar cuando el servidor caduca
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "No pudo cerrar sesión", Toast.LENGTH_SHORT).show());
    }

    private List<Sala> GetData() {
        lst = new ArrayList<>();

        lst.add(new Sala(1,R.drawable.fotoadminision,"Sala de Admisión",""));
        lst.add(new Sala(2,R.drawable.fotopolideportivo, "Sala de Polidepotivo",""));
        lst.add(new Sala(3,R.drawable.pagosmatrpens, "Sala de Pagos",""));
        lst.add(new Sala(4,R.drawable.markadmienf,"Sala de Administración",""));
        lst.add(new Sala(5,R.drawable.escuelaambiental, "Sala de Ingeniería",""));
        lst.add(new Sala(6,R.drawable.ladquimica, "Sala de Química",""));


        /*
        lst.add(new Sala(1,R.drawable.entrada_a_puesto_de_vigilancia,"Entrada a puesto de vigilancia",""));
        lst.add(new Sala(2,R.drawable.cafeteria, "Cafeteria",""));
        lst.add(new Sala(3,R.drawable.oficina_de_vigilancia, "Oficina de vigilancia",""));
*/
        return lst;
    }
}