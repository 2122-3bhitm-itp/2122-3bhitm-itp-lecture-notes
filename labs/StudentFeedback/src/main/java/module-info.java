module at.htl.survey {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.derby.client;
    requires org.apache.derby.commons;
    requires org.apache.derby.tools;
    requires org.mybatis;

    opens at.htl.survey.view to javafx.fxml;
    exports at.htl.survey.view;
    exports at.htl.survey.controller;
    exports at.htl.survey.model;
}