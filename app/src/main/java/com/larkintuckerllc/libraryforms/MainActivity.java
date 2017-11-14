package com.larkintuckerllc.libraryforms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import java.util.List;


public class MainActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty(message = "Required")
    private EditText mFirstField;

    @NotEmpty(message = "Required")
    private EditText mLastField;

    @Email(message = "Valid Email Required")
    private EditText mEmailField;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirstField = findViewById(R.id.activity_main__txt_first);
        mFirstField.addTextChangedListener(new FieldValidator());
        mLastField = findViewById(R.id.activity_main__txt_last);
        mLastField.addTextChangedListener(new FieldValidator());
        mEmailField = findViewById(R.id.activity_main__txt_email);
        mEmailField.addTextChangedListener(new FieldValidator());
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    public void sendFeedback(View view) {
        String first = mFirstField.getText().toString();
        String last = mLastField.getText().toString();
        String email = mEmailField.getText().toString();
        Log.d("DEBUG", first);
        Log.d("DEBUG", last);
        Log.d("DEBUG", email);
    }

    @Override
    public void onValidationSucceeded() {
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }
        }
    }

    private class FieldValidator implements TextWatcher {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validator.validate();
        }

        public void afterTextChanged(Editable s) {
        }
    }

}
