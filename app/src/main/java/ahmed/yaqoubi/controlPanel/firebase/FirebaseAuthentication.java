package ahmed.yaqoubi.controlPanel.firebase;

import android.app.Activity;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ahmed.yaqoubi.controlPanel.callback.CallBack;
import ahmed.yaqoubi.controlPanel.model.User;

public class FirebaseAuthentication {
    private Activity activity;
    private static FirebaseAuthentication instance;
    private FirebaseAuth mAuth;
    private FirebaseDatabase rootNode;
    private String userId;
    private FirebaseUser currentUser;

    private FirebaseAuthentication(Activity activity, String userId) {
        this.activity = activity;
        this.mAuth = FirebaseAuth.getInstance();
        this.rootNode = FirebaseDatabase.getInstance();
        this.userId = userId;
        if (userId != null)
            this.currentUser = mAuth.getCurrentUser();
    }


    public static FirebaseAuthentication getInstance(Activity activity, String userId) {
        if (instance == null) {
            instance = new FirebaseAuthentication(activity, userId);

        }
        return instance;
    }


    public void signUp(final User user, CallBack callBack) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            currentUser = mAuth.getCurrentUser();

                            rootNode.getReference("control").child("user").child(currentUser.getUid()).child("info").setValue(user);
                            callBack.getResult("done");
                        } else {
                            callBack.getResult("fail");
                        }

                    }
                });
    }

    public void updaterUser(FirebaseUser user) {// space = 32
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName("anyname").setPhotoUri(Uri.parse("anyData"))
                .build();
        user.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    }

    public void logIn(String email, String password, CallBack callBack) {
        final String[] userId = {null};
        this.mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                currentUser = mAuth.getCurrentUser();
                callBack.getResult(currentUser.getUid());

            }
        }).addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.getResult("fail");

            }
        });
    }


    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }
}
