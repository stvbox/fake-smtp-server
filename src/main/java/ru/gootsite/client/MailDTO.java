package ru.gootsite.client;

import lombok.Data;

import java.io.Serializable;

@Data
public class MailDTO implements Serializable {
    public String date;
    public String subject;
    public String body;
    public String headers;
}
