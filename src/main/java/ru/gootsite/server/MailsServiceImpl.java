package ru.gootsite.server;

import com.dumbster.smtp.SimpleSmtpServer;
import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.QuotedPrintableCodec;
import ru.gootsite.client.MailDTO;
import ru.gootsite.client.MailsService;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class MailsServiceImpl extends RemoteServiceServlet implements MailsService {
    private static SimpleSmtpServer smtpServer;
    private static Gson gson;

    public static void setSmtpServer(SimpleSmtpServer server) {
        MailsServiceImpl.smtpServer = server;
    }

    public static void setGson(Gson gson) {
        MailsServiceImpl.gson = gson;
    }

    @Override
    public List<MailDTO> getMails() {
        log.info("Сервер: {}", smtpServer.toString());

        // /fake-smtp-server/get-mail/0.eml
        AtomicInteger index = new AtomicInteger(0);
        return smtpServer.getReceivedEmails().stream().map((mail) -> {
            String url = "/fake-smtp-server/get-mail/" + index.getAndIncrement() + ".eml";

            Map headers = new HashMap();
            mail.getHeaderNames().forEach((headerName) -> {
                headers.put(headerName, mail.getHeaderValues(headerName));
            });

            MailDTO mailDTO = new MailDTO();
            mailDTO.setUrl(url);
            mailDTO.setDate(mail.getHeaderValue("Date"));
            mailDTO.setSubject(mail.getHeaderValue("Subject"));
            mailDTO.setBody(mail.getBody());
            mailDTO.setHeaders(gson.toJson(headers));

            return mailDTO;
        }).collect(Collectors.toList());

    }
}
