package com.mongooze.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import java.util.HashSet;
import java.util.Set;

public class Helper {
    public static String getAccount(Context context) {
        AccountManager accountManager = AccountManager.get(context);

        if(accountManager != null){
            Account[] accounts = accountManager.getAccounts();
        }

        return "Name";
    }

    static final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";

    static final java.util.Random rand = new java.util.Random();

    // consider using a Map<String,Boolean> to say whether the identifier is being used or not
    static final Set<String> identifiers = new HashSet<String>();

    public static String randomIdentifier() {
        StringBuilder builder = new StringBuilder();
        while(builder.toString().length() == 0) {
            int length = rand.nextInt(5)+5;
            for(int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if(identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }
}
