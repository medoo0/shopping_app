package com.alaa.microprocess.lrahtk.Fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Contract.MainActivityContract;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.HomePage;
import com.alaa.microprocess.lrahtk.pojo.LoginForm;
import com.alaa.microprocess.lrahtk.pojo.RegisterForm;
import com.alaa.microprocess.lrahtk.pojo.RegisterResponse;
import com.alaa.microprocess.lrahtk.pojo.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends Fragment implements View.OnClickListener{

    TextView skiptoSignUP;
    MainActivityContract.View mainView;
    ImageView skiptosignupimage;
    Button button;
    EditText email,password  ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signin_layout,container,false);

        skiptoSignUP      = view.findViewById(R.id.skiptoSignUP);
        skiptosignupimage = view.findViewById(R.id.skiptosignupimage);
        button            = view.findViewById(R.id.email_sign_in_button);
        password          = view.findViewById(R.id.password);
        email             = view.findViewById(R.id.email);
        skiptosignupimage.setOnClickListener(this);
        skiptoSignUP.setOnClickListener(this);
        button.setOnClickListener(this);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setTypeface(Typeface.DEFAULT);
        return view;
    }

    @Override
    public void onClick(View view) {



        if (view == button){


            if (email.getText().toString().isEmpty()){


                // from string file (الحقل فارغ)

                email.setError(getString(R.string.email_error));

            }



            if (!isEmailValid(email.getText().toString())){

                // from string file  (minLength: 6)
                email.setError(getString(R.string.email_error2));

            }

            if (password.getText().toString().isEmpty()){

                // from string file (الحقل فارغ)
                password.setError(getString(R.string.password_error));

            }

            if (password.getText().toString().length()<6){
                // from string file  (minLength: 6)
                password.setError(getString(R.string.password_error2));

            }

            if (!email.getText().toString().isEmpty()  &&  isEmailValid(email.getText().toString())
                    && !password.getText().toString().isEmpty()  &&  password.getText().toString().length()>6){


                // create progressbar until Library had been finished //


                RegisterForm registerForm = new RegisterForm();

                registerForm.setEmail(email.getText().toString());
                registerForm.setPassword(password.getText().toString());
                ApiMethod client = ApiRetrofit.getRetrofit().create(ApiMethod.class);

                retrofit2.Call<LoginForm> call = client.login(registerForm.getCreatedAt(),registerForm.getUpdatedAt(),registerForm.getId()
                        ,registerForm.getEmail(),registerForm.getPassword());

               call.enqueue(new Callback<LoginForm>() {
                   @Override
                   public void onResponse(@NonNull Call<LoginForm> call,@NonNull Response<LoginForm> response) {




                       if (response.isSuccess()&&response.body()!=null){

                           // hide progressbar and go to NextScreen

                           Intent intent = new Intent(getActivity() , HomePage.class);
                           User user    = new User();
                           user.setEmail(response.body().getUser().getEmail());
                           user.setId(response.body().getUser().getId());
                           user.setName(response.body().getUser().getName());
                           user.setPhone(response.body().getUser().getPhone());
                           intent.putExtra("Email",user.getEmail());
                           intent.putExtra("id",user.getId());
                           intent.putExtra("Name",user.getName());
                           intent.putExtra("Phone",user.getPhone());
                           startActivity(intent);

                       }

                       else {

                           // email or password error check your private data //

                           email.setError("");

                           // hide progressbar and still in this Screen //

                       }



                   }

                   @Override
                   public void onFailure(@NonNull Call<LoginForm> call, @NonNull Throwable t) {
                       // connection poor or exception in retrofit occur .... //
                       email.setError("");

                   }
               });




            }





        }



        if (view  == skiptoSignUP){


            mainView = (MainActivityContract.View) getActivity();
            if (mainView!=null){

                mainView.getViewPager().setCurrentItem(2);


            }


        }

        if (view == skiptosignupimage){


            mainView = (MainActivityContract.View) getActivity();
            if (mainView!=null){

                mainView.getViewPager().setCurrentItem(2);
//                mainView.openSignupFragment();

            }

        }
    }
    boolean isEmailValid(CharSequence email) {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches(); //returen false if not ok //return true if ok

    }

}
