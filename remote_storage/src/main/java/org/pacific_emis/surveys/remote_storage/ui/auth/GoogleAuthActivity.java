package org.pacific_emis.surveys.remote_storage.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.pacific_emis.surveys.remote_storage.R;
import org.pacific_emis.surveys.remote_storage.data.accessor.RemoteStorageAccessor;
import org.pacific_emis.surveys.remote_storage.di.RemoteStorageComponentInjector;

public class GoogleAuthActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SIGN_IN = 987;

    private RemoteStorageAccessor accessor;

    public static Intent createIntent(Context context) {
        return new Intent(context, GoogleAuthActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_auth_activty);
        accessor = RemoteStorageComponentInjector.getComponent(getApplication()).getRemoteStorageAccessor();
        signIn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                accessor.onGoogleSignInAccountReceived(account);
            } catch (ApiException e) {
                e.printStackTrace();
                accessor.onGoogleSignInAccountReceived(null);
            }
            finish();
        }
    }

    private void signIn() {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient client = GoogleSignIn.getClient(this, options);
        startActivityForResult(client.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }
}
