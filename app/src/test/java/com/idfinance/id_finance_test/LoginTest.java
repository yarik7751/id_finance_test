package com.idfinance.id_finance_test;

import android.graphics.Bitmap;

import com.idfinance.id_finance_test.interactor.MainInteractor;
import com.idfinance.id_finance_test.presenter.MainPresenter;
import com.idfinance.id_finance_test.relations.MainRelations;
import com.idfinance.id_finance_test.router.MainRouter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(sdk = {21}, manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class LoginTest {

    MainRelations.IView view;
    MainInteractor iteractor;
    MainRelations.IInteractorOutput interactorOutput;
    MainRouter router;

    @Before
    public void init() {
        view = Mockito.mock(MainRelations.IView.class);
        iteractor = Mockito.mock(MainInteractor.class);
        interactorOutput = Mockito.mock(MainRelations.IInteractorOutput.class);
        router = Mockito.mock(MainRouter.class);
    }

    @Test
    public void emptyDataTest() {
        String login = "", password = "";

        MainPresenter presenter = new MainPresenter(iteractor, router, view);
        presenter.onLoginClickButton(login, password);
        Mockito.verify(view).showEmptyDataError();
    }

    @Test
    public void emptyLoginTest() {
        String login = "", password = "qwertyuio";

        MainPresenter presenter = new MainPresenter(iteractor, router, view);
        presenter.onLoginClickButton(login, password);
        Mockito.verify(view).showEmptyDataError();
    }

    @Test
    public void emptyPasswordTest() {
        String login = "zxcvbnm", password = "";

        MainPresenter presenter = new MainPresenter(iteractor, router, view);
        presenter.onLoginClickButton(login, password);
        Mockito.verify(view).showEmptyDataError();
    }

    @Test
    public void wrongDataTest() {
        String login = "kate", password = "fghtre";

        MainInteractor iteractor = new MainInteractor(interactorOutput);
        MainPresenter presenter = new MainPresenter(iteractor, router, view);
        presenter.onLoginClickButton(login, password);
        Mockito.verify(interactorOutput).loginError();
    }

    @Test
    public void rightDataTest() {
        String login = "yarik", password = "123456";

        MainInteractor iteractor = new MainInteractor(interactorOutput);
        MainPresenter presenter = new MainPresenter(iteractor, router, view);
        presenter.onLoginClickButton(login, password);
        Mockito.verify(interactorOutput).loginSuccess(login, password);
    }
}
