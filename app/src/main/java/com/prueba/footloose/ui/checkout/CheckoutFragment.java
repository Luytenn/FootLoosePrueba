package com.prueba.footloose.ui.checkout;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.OrderRequest;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;
import com.prueba.footloose.R;
import com.prueba.footloose.adapter.AdapterCart;
import com.prueba.footloose.adapter.AdapterCheckout;
import com.prueba.footloose.db.ConsultaCart;
import com.prueba.footloose.model.Producto;
import com.prueba.footloose.ui.catalogo.CatalogoFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;


public class CheckoutFragment extends Fragment {

    RecyclerView recyclerCheckout;
    AdapterCheckout adapterCheckout;
    PaymentButtonContainer btnPayment;
    TextView totalAmount;

    String cliendId = "AWWcia9_Nd6NwoPKH5yHSUe1InzD1OhUv3WtpBZgezzOerg9ItCaU7vJ3t24ZCpY_j_6uj2ZCkdhk-ro";

    int PAYPAL_REQUEST_CODE = 123;

    public static PayPalConfiguration configuration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_checkout,container, false);


        btnPayment = root.findViewById(R.id.payment_button_container);


        btnPayment.setup(
                createOrderActions -> {
                    ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                    purchaseUnits.add(
                            new PurchaseUnit.Builder()
                                    .amount(
                                            new Amount.Builder()
                                                    .currencyCode(CurrencyCode.USD)
                                                    .value(totalAmount.getText().toString())
                                                    .build()
                                    ).build()
                    );
                    OrderRequest order = new OrderRequest(
                            OrderIntent.CAPTURE,
                            new AppContext.Builder()
                                    .userAction(UserAction.PAY_NOW)
                                    .build(),
                            purchaseUnits
                    );
                    createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                },
                approval -> {
                    Log.i("TAG", "OrderId: " + approval.getData().getOrderId());
                    new ConsultaCart(getContext()).deleteAll();
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new CatalogoFragment()).commit();

                }


        );


        recyclerCheckout = root.findViewById(R.id.recyclerViewCheckout);

        mostrarData(root);

        return root;

    }



    private void mostrarData(View root) {

        ConsultaCart proCart = new ConsultaCart(getContext());
        ArrayList<Producto> productoLista =  proCart.mostrarProductos();

        totalAmount = root.findViewById(R.id.txt_totalAmount);

        totalAmount.setText(String.valueOf(proCart.totalCartItems()));
        recyclerCheckout.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterCheckout = new AdapterCheckout(getContext(), productoLista);
        recyclerCheckout.setAdapter(adapterCheckout);


        adapterCheckout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });


    }


    private void showDialog(){

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);


        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        dialog.show();

    }

    private void getPayment() {

        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(1000)),"USD", "Reto footloose",PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(getContext(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);

        startActivityForResult(intent,PAYPAL_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQUEST_CODE) {

            PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

            if (paymentConfirmation != null) {

                String paymentDetails = paymentConfirmation.toJSONObject().toString();

                try {
                    JSONObject object = new JSONObject(paymentDetails);
                } catch (JSONException e) {
                    Toast.makeText(getContext(),e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

            } else if (requestCode == Activity.RESULT_CANCELED) {

                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }

        } else if(requestCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

            Toast.makeText(getContext(), "Invalid payment", Toast.LENGTH_SHORT).show();

        }

    }
}