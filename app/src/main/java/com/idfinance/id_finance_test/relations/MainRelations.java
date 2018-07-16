package com.idfinance.id_finance_test.relations;

public class MainRelations {

    public interface IView {
        void showEmptyDataError();
        void showError();
    }

    public interface IPresenter {
        void onLoginClickButton(String login, String password);
        void vkAuthorization();
    }

    public interface IInteractor {
        void login(String login, String password);
    }

    public interface IInteractorOutput {
        void loginSuccess(String login, String password);
        void loginError();
    }

    public interface IRouter {
        void showHomeActivity();
    }
}
