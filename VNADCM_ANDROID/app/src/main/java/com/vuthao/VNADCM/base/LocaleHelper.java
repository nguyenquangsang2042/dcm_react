package com.vuthao.VNADCM.base;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;

import java.util.Locale;

public class LocaleHelper extends ContextWrapper {

    public LocaleHelper(Context base) {
        super(base);
    }

    public static ContextWrapper wrap(Context context) {
        Configuration config = context.getResources().getConfiguration();

        SharedPreferencesController sharedPreferencesController = new SharedPreferencesController(context);
        String language = sharedPreferencesController.getLocaleId();
        language = language.equals("1066") ? "vi" : "en";

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        setSystemLocale(config, locale);
        config.setLayoutDirection(locale);
        context = context.createConfigurationContext(config);
        return new LocaleHelper(context);
    }

    public static ContextWrapper wrap(Context context, String language) {
        Configuration config = context.getResources().getConfiguration();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        setSystemLocale(config, locale);
        config.setLayoutDirection(locale);
        context = context.createConfigurationContext(config);
        return new LocaleHelper(context);
    }

    public static void setSystemLocale(Configuration config, Locale locale) {
        config.setLocale(locale);
    }
}
