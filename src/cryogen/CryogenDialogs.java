package cryogen;

/**
 * @author Zander Labuschagne
 * E-Mail: <zander.labuschagne@protonmail.ch>
 */

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
//TODO: Ek sal die 'n super klas moet maak en alerts, dialogs en notifications moet extend vanaf die een
//TODO: Ek moet dalk die showAndWait() in aparte funksie sit??

public class CryogenDialogs extends Alert
{
	public CryogenDialogs(Alert.AlertType type, String title, String headerText, String contentText)
	{
		super(type);
		super.setTitle(title);
		super.setHeaderText(headerText);
		super.setContentText(contentText);

		//Three lines to add CSS styling to dialogs
		DialogPane dialogPane = super.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("MidnaDark.css").toExternalForm());
		dialogPane.getStyleClass().add("alert");
	}
}
