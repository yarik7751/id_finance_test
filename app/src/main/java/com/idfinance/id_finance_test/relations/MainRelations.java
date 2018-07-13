package com.idfinance.id_finance_test.relations;

public class MainRelations {

    public static interface IView {
        void showError();
    }

    public static interface IPresenter {
        void onLoginClickButton(String login, String password);
    }

    public static interface IInteractor {
        void login(String login, String password);
    }

    public static interface IInteractorOutput {
        void loginSuccess();
        void loginError();
    }

    public static interface IRouter {
        void showHomeActivity();
    }
}
