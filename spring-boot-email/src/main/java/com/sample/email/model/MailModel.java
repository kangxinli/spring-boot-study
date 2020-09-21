package com.sample.email.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class MailModel {

    private String from;
    private String to;
    private String name;
    private String subject;
    private String content;
    private Map<String, String> model;
}
