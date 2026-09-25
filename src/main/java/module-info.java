module uqvirutal.edu.co.parcialunop2 {
    requires javafx.controls;
    requires javafx.fxml;

    // Clases principales y controladores
    exports uqvirutal.edu.co.parcialunop2;
    opens uqvirutal.edu.co.parcialunop2 to javafx.fxml;

    exports uqvirutal.edu.co.parcialunop2.controller;
    opens uqvirutal.edu.co.parcialunop2.controller to javafx.fxml;

    // AGREGAR ESTAS DOS LÍNEAS PARA EL MODELO:
    exports uqvirutal.edu.co.parcialunop2.model;
    opens uqvirutal.edu.co.parcialunop2.model to javafx.fxml, javafx.base;
}