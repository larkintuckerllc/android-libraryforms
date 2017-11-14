package com.larkintuckerllc.libraryforms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private Button mSubmitButton;
    private Validator mValidator;
    private Boolean mFirstFieldDirty = false;
    private Boolean mLastFieldDirty = false;
    private Boolean mEmailFieldDirty = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirstField = findViewById(R.id.activity_main__txt_first);
        mFirstField.addTextChangedListener(new FieldValidator());
        mFirstField.setOnFocusChangeListener(new FieldFocusChangeListener());
        mLastField = findViewById(R.id.activity_main__txt_last);
        mLastField.addTextChangedListener(new FieldValidator());
        mLastField.setOnFocusChangeListener(new FieldFocusChangeListener());
        mEmailField = findViewById(R.id.activity_main__txt_email);
        mEmailField.addTextChangedListener(new FieldValidator());
        mEmailField.setOnFocusChangeListener(new FieldFocusChangeListener());
        mSubmitButton = findViewById(R.id.activity_main__btn_submit);
        mSubmitButton.setEnabled(false);
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
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
        mSubmitButton.setEnabled(true);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        mSubmitButton.setEnabled(false);
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                switch(view.getId()) {
                    case R.id.activity_main__txt_first:
                        if (mFirstFieldDirty)
                            ((EditText) view).setError(message);
                            break;
                    case R.id.activity_main__txt_last:
                        if (mLastFieldDirty)
                            ((EditText) view).setError(message);
                            break;
                    case R.id.activity_main__txt_email:
                        if (mEmailFieldDirty)
                            ((EditText) view).setError(message);
                            break;
                }
            }
        }
    }

    private class FieldValidator implements TextWatcher {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mValidator.validate();
        }

        public void afterTextChanged(Editable s) {
        }
    }

    private class FieldFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) return;
            switch(view.getId()) {
                case R.id.activity_main__txt_first:
                    mFirstFieldDirty = true;
                    break;
                case R.id.activity_main__txt_last:
                    mLastFieldDirty = true;
                    break;
                case R.id.activity_main__txt_email:
                    mEmailFieldDirty = true;
                    break;
            }
            mValidator.validate();
        }
    }

}
