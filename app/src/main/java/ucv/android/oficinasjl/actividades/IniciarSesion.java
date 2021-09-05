package ucv.android.oficinasjl.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ucv.android.oficinasjl.R;
import ucv.android.oficinasjl.utilidades.Constants;
import ucv.android.oficinasjl.utilidades.PreferenceManager;

// la clase se convierte en una actividad por las propiedades que tiene
public class IniciarSesion extends AppCompatActivity {
// creacion de variables
    private EditText inputEmail, inputContraseña;
    private MaterialButton btnSignIn;
    private ProgressBar signInProgressBar;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        findViewById(R.id.contacta_aqui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        });
        // se realiza la instancia de una clase
        preferenceManager = new PreferenceManager(getApplicationContext());
        signInProgressBar = findViewById(R.id.signInProgressBar);
        inputEmail = findViewById(R.id.inputEmail);
        inputContraseña = findViewById(R.id.inputContraseña);
        btnSignIn = findViewById(R.id.btnSignIn);

        // validacion nuestra y otra de android
        btnSignIn.setOnClickListener(view -> {
            if (inputEmail.getText().toString().trim().isEmpty()) {
                Toast.makeText(IniciarSesion.this, "Falta el email", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches()) { //quiza haya varianteS
                Toast.makeText(IniciarSesion.this, "Falta validar email", Toast.LENGTH_SHORT).show();
            } else if (inputContraseña.getText().toString().trim().isEmpty()) {
                Toast.makeText(IniciarSesion.this, "Falta contraseña", Toast.LENGTH_SHORT).show();
            } else {
                signIn();
            }
        });
    }
    private void signIn() {
        btnSignIn.setVisibility(View.INVISIBLE); //al validar t@do el boton desaparece
        signInProgressBar.setVisibility(View.VISIBLE); // y la barra de progreso aparece

        //se conecta a firebase
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        // en utilidades encontramos Constants. Constants.KEY_COLLECION_USER = usuarios
        // collection es como una tabla
        database.collection(Constants.KEY_COLLECION_USERS)
                // selecciona los correos y contraseñas
                // select correo, contraseña from usuarios where correo=inputcorreo and contraseña=inputcontraseña
                .whereEqualTo(Constants.KEY_EMAIL,inputEmail.getText().toString().trim())
                .whereEqualTo(Constants.KEY_CONTRASEÑA,inputContraseña.getText().toString().trim())
                .get()
                .addOnCompleteListener(task -> {
                    // documents = una fila = un usuario
                    if (task.isSuccessful() && task.getResult() !=null && task.getResult().getDocuments().size()>0){ // getDocuments().size()>0 indica que hay 1 usuarios
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0); // va a tomar una sola fila si ha resolvido
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true); // el servidor de firebase ha iniciado sesion con un usuario
                        preferenceManager.putString(Constants.KEY_USER_ID,documentSnapshot.getId()); // selecciona esta constante que indicamos que es el id
                        preferenceManager.putString(Constants.KEY_NOMBRE,documentSnapshot.getString(Constants.KEY_NOMBRE)); // selecciona esta constante y las demas también
                        preferenceManager.putString(Constants.KEY_CARGO,documentSnapshot.getString(Constants.KEY_CARGO)); // se guardan en preference
                        preferenceManager.putString(Constants.KEY_EMAIL,documentSnapshot.getString(Constants.KEY_EMAIL)); //  manager de tipo cadena
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class); // inicia la intención de cargar el contexto de un java simple
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // se necesita cargar desde el contexto de la clase
                        startActivity(intent); // carga la intencion
                    }else {
                        signInProgressBar.setVisibility(View.INVISIBLE); // desaparece
                        btnSignIn.setVisibility(View.VISIBLE); // aparece
                        Toast.makeText(IniciarSesion.this,"DATOS INCORRECTOS",Toast.LENGTH_SHORT).show(); // muestra el mensaje porque el servidor esta
                                                                                                                     // caducado o los datos son incorrectos
                    }
                });
    }
}