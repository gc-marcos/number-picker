package com.example.number_picker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String ARQUIVO_MEUS_DADOS = "MeusDados";
    private TextView mTextViewSaldo;
    private EditText mEditTextValor;
    private NumberPicker numberPicker;
    private RadioGroup mRadioGrup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewSaldo = findViewById(R.id.textView2);
        mEditTextValor = findViewById(R.id.editTextText);
        mRadioGrup = findViewById(R.id.radioGroup);
        Button mbutton = findViewById(R.id.button);
        numberPicker = findViewById(R.id.numberPicker);

        numberPicker.setMinValue(2013);
        numberPicker.setMaxValue(2030);

        numberPicker.setOnValueChangedListener((numberPicker, oldValue, newValue) -> exibirSaldo(newValue));

        mbutton.setOnClickListener(view -> {
            String valorStr = mEditTextValor.getText().toString();
            if (!valorStr.isEmpty()) {
                float valor = Float.parseFloat(valorStr);
                int ano = numberPicker.getValue();
                int checkRadioButtonId = mRadioGrup.getCheckedRadioButtonId();
                if (checkRadioButtonId == R.id.radioButton) {
                    adicionarValor(ano, valor);
                } else if (checkRadioButtonId == R.id.radioButton2) {
                    excluirValor(ano, valor);
                }
                exibirSaldo(ano);
            }
        });
    }

    private void adicionarValor(int ano, float valor) {
        SharedPreferences sharedPreferences =
                getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        float valorAtual = sharedPreferences.getFloat(String.valueOf(ano), 0);
        float novoValor = valorAtual + valor;
        sharedPreferences.edit().putFloat(String.valueOf(ano), novoValor).apply();
    }

    private void excluirValor(int ano, float valor) {
        SharedPreferences sharedPreferences =
                getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        float valorAtual = sharedPreferences.getFloat(String.valueOf(ano), 0);
        float novoValor = valorAtual - valor;
        if (novoValor < 0) {
            novoValor = 0;
        }
        sharedPreferences.edit().putFloat(String.valueOf(ano), novoValor).apply();
    }

    private void exibirSaldo(int ano) {
        SharedPreferences sharedPreferences =
                getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        float saldo = sharedPreferences.getFloat(String.valueOf(ano), 0);
        mTextViewSaldo.setText(String.format("R$ %.2f", saldo));
    }
}








