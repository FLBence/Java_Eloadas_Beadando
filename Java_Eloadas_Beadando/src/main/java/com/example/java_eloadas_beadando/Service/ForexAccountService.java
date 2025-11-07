package com.example.java_eloadas_beadando.Service;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;
import com.oanda.v20.account.AccountSummaryResponse;
import org.springframework.stereotype.Service;

@Service
public class ForexAccountService {
    private static final String API_URL = "https://api-fxpractice.oanda.com";
    private static final String ACCESS_TOKEN = "02111978fe023333a51c5a2d558825eb-e460c15f3f86b734e333e94e1032e819";
    private static final String ACCOUNT_ID = "101-004-37603372-002";

    public AccountSummary getAccountSummary() throws Exception {
        Context ctx = new Context(API_URL, ACCESS_TOKEN);
        AccountSummaryResponse response = ctx.account.summary(new AccountID(ACCOUNT_ID));
        return response.getAccount();
    }
}
