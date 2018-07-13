package com.idfinance.id_finance_test.interactor;

import com.idfinance.id_finance_test.relations.MainRelations;

public class MainInteractor implements MainRelations.IInteractor {

    private String[] logins = {
            "yarik",
            "vova"
    };

    private String[] passwords = {
            "123456",
            "qwerty"
    };

    private MainRelations.IInteractorOutput interactorOutput;

    public MainInteractor(MainRelations.IInteractorOutput interactorOutput) {
        this.interactorOutput = interactorOutput;
    }

    @Override
    public void login(String login, String password) {
        for(int i = 0; i < logins.length; i++) {
            if(logins[i].equals(login) & passwords[i].equals(password)) {
                interactorOutput.loginSuccess();
                return;
            }
        }
        interactorOutput.loginError();
    }
}
