package com.sandstrom.CustomClasses;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class StylesNotCoveredByCSS {


        public void headerLabel(Label label) {
            label.setAlignment(Pos.CENTER);
            label.setMinSize(300, 30);
            label.setMaxSize(300, 30);
            label.setStyle(
                    "-fx-text-fill: #67040C;"
                            + "-fx-alignment: center;"
                            + "-fx-padding: 40px 0 0 0;"
                            + "-fx-min-height: 80;"
                            + "-fx-max-height: 80;"
                            + "-fx-background-radius: 3px;"
                            + "-fx-font-size:24pt;"
                            + "	-fx-font-family: 'Agency FB'");

           // Agency FB'
        }

        public void errorLabel (Label label ){
            label.setMinHeight(30);
            label.setMaxHeight(30);
            label.setPadding(new Insets(10));
            label.setStyle("-fx-font-size: 10; -fx-font-family: " +
                    "Arial Narrow; -fx-text-fill: #ab5e0b;");

        }

        public void backButton (Button button){

           button.setStyle(" -fx-font-size: 14px;"
           + "-fx-font-family: Arial Narrow;"
           + "-fx-background-color: #EFB5CA;"
           + "-fx-text-fill: #67040C;"
           + "-fx-border-color: #67040C;"
           + "-fx-min-width: 100px;"
           + "-fx-max-width: 100px;"
           + "-fx-min-height: 40px;"
           + "-fx-max-height: 40px;"
           + "-fx-background-radius: 3, 3, 3, 3;"
           + "-fx-border-radius: 3, 3, 3, 3;"
           + "-fx-border-width: 1px;");
        }

        public void textArea (TextArea textArea){
            textArea.setStyle("-fx-text-fill: #67040C;"
                    +" -fx-background-color: #EFB5CA;"
                    + "-fx-font-family: Arial Narrow;"
                    + "-fx-font-size: 11;"
                    + "-fx-background-radius: 3, 3, 3, 3;"
                    + "-fx-border-radius: 3, 3, 3, 3;"
                    + "-fx-border-color : #67040C"
                  );

        }


    }

