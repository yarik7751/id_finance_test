package com.idfinance.id_finance_test.presenter;

import com.idfinance.id_finance_test.interactor.MainInteractor;
import com.idfinance.id_finance_test.relations.MainRelations;
import com.idfinance.id_finance_test.router.MainRouter;
import com.idfinance.id_finance_test.view.main.MainActivity;

public class MainPresenter implements MainRelations.IPresenter, MainRelations.IInteractorOutput {

    private MainRelations.IInteractor interactor;
    private MainRelations.IRouter router;
    private MainRelations.IView view;

    public MainPresenter(MainRelations.IView view) {
        this.view = view;
        interactor = new MainInteractor(this);
        router = new MainRouter((MainActivity) view);
    }

    @Override
    public void onLoginClickButton(String login, String password) {
        interactor.login(login, password);
    }

    @Override
    public void loginSuccess() {
        router.showHomeActivity();
    }

    @Override
    public void loginError() {
        view.showError();
    }
}