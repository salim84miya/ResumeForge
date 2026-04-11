package com.springai.resumax.chat.entity;

import lombok.Data;

import java.util.List;

@Data
public class Experience {

    private String organization; // company / org
    private String timeline;
    private List<String> description;

}
