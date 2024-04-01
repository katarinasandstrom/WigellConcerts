package com.sandstrom.CustomClasses;

import javafx.scene.control.TextField;

public class CustomTextField extends TextField {

    public CustomTextField() {
        initialize();
    }

    private void initialize() {
        getStyleClass().add("textField");
    }
}

