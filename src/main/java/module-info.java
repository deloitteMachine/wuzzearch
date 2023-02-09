module com.deloitte.wuzzearch {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;
	requires org.fxmisc.richtext;
	requires reactfx;

    opens com.deloitte.wuzzearch to javafx.fxml;
    exports com.deloitte.wuzzearch;
}
