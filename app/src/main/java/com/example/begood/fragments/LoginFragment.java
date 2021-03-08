package com.example.begood.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.begood.MainActivity;
import com.example.begood.R;
import com.example.begood.models.Model;
import com.example.begood.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.io.Serializable;

public class LoginFragment extends Fragment implements View.OnClickListener , Serializable {
    public static GoogleSignInClient mGoogleSignInClient;
    public static GoogleSignInAccount account;
    private static final String TAG = "Main Fragment";
    private static final int RC_SIGN_IN = 7;
    User user;
    SignInButton signInButton;
    View view;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_login, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().hide();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        account = GoogleSignIn.getLastSignedInAccount(view.getContext());
        signInButton = view.findViewById(R.id.login_button);

        TextView textView = (TextView) signInButton.getChildAt(0);
        textView.setText("התחברות עם חשבון גוגל");

        view.findViewById(R.id.login_button).setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI(account);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                // Check for existing Google Sign In account, if the user is already signed in
                // the GoogleSignInAccount will be non-null.
                if (account == null){
                    signIn();
                    account = GoogleSignIn.getLastSignedInAccount(v.getContext());
                } else {
                    updateUI(account);
                }

                break;
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
             user = new User(
                     account.getId(),
                     account.getEmail(),
                     account.getDisplayName(),
                     account.getPhotoUrl().toString()
             );

            Model.instance.AddUser(user, () -> {            // Signed in successfully, show authenticated UI.
                updateUI(account);
            });
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(Object o) {
        if (o instanceof GoogleSignInAccount) {
            String userId = ((GoogleSignInAccount) o).getId();
            //Model.instance.GetUserById(userId, user -> {
//                if(user != null){
                    //TODO: pass user in navAction instead of get from db!
//                    NavGraphDirections.ActionGlobalFeedFrg directions = LoginFragmentDirections.actionGlobalFeedFrg(user);
//                    Navigation.findNavController(view).navigate(directions);
                    Navigation.findNavController(view).navigate(R.id.action_global_feedFrg);

//                } else {
//                    Log.d("Error", "couldn't find user with ID:" + userId);
//                }
            //});
        } else {
            Log.i(TAG, "UI updated");
        }
    }

    public static GoogleSignInClient getmGoogleSignInClient(){
        return  mGoogleSignInClient;
    }
    public static GoogleSignInAccount getAccount(){
        return  account;
    }
}