package ru.gootsite.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("stub-mails")
public interface MailsService extends RemoteService {
    List<MailDTO> getMails();
}
