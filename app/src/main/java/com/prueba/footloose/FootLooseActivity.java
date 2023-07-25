package com.prueba.footloose;

import android.app.Application;
import android.util.Log;

import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.PaymentButtonIntent;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.config.UIConfig;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;

public class FootLooseActivity  extends Application {

    String clientId = "AcRzQjwrpGlC6Frtw-LbLc9VvIEDxewngM2ZI2L47i8wqA8jOk6TyOoVPCCvK28GKWxPImwoPJWsOtLu";
    String returnUrl = "nativexo://paypalpay";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("MAIN", "INGRESO MAINACTIVITY");

        PayPalCheckout.setConfig(new CheckoutConfig(
                this,
                clientId,
                Environment.SANDBOX,
                CurrencyCode.USD,
                UserAction.PAY_NOW,
                PaymentButtonIntent.CAPTURE,
                new SettingsConfig(true, false),
                new UIConfig(true),
                returnUrl
        ));



    }
}
