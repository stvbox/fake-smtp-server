package ru.gootsite.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface MailsServiceAsync {
    void getMails(AsyncCallback<List<MailDTO>> async);
}
