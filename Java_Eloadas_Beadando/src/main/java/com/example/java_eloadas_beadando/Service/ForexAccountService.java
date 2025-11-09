package com.example.java_eloadas_beadando.Service;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;
import com.oanda.v20.account.AccountSummaryResponse;
import org.springframework.stereotype.Service;

@Service
public class ForexAccountService {
    private static final String API_URL = "https://api-fxpractice.oanda.com";
    private static final String ACCESS_TOKEN = "aa1995990e625e7188557f4c7a5f9b0d-614a67cdaecdeb9a2a3290356550b1cf";
    private static final String ACCOUNT_ID = "101-004-37623364-001";

    public AccountSummary getAccountSummary() throws Exception {
        Context ctx = new Context(API_URL, ACCESS_TOKEN);
        AccountSummaryResponse response = ctx.account.summary(new AccountID(ACCOUNT_ID));
        return response.getAccount();
    }
}
