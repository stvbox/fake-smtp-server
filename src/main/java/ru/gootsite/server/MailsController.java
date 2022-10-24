package ru.gootsite.server;

import com.dumbster.smtp.SimpleSmtpServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailsController {
    private final SimpleSmtpServer simpleSmtpServer;

    public MailsController(SimpleSmtpServer simpleSmtpServer) {
        this.simpleSmtpServer = simpleSmtpServer;
    }

    @GetMapping(value = "/test")
    String test() {
        return "test1";
    }

    @GetMapping(value = "/get-mail/{index}.eml", produces = "eml/text; charset=UTF-8")
    String getMail(@PathVariable(value = "index") Integer index) {
        return simpleSmtpServer.getReceivedEmails().get(index).toString();
    }
}
