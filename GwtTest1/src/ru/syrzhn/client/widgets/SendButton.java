package ru.syrzhn.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import ru.syrzhn.client.GwtTest1;
import ru.syrzhn.client.dialogs.UserInfoDialog;
import ru.syrzhn.shared.FieldVerifier;

public class SendButton extends Button {
	private UserInfoDialog userInfoDlgBox;
	public void setUserInfoDlgBox(UserInfoDialog userInfoDlgBox) {
		this.userInfoDlgBox = userInfoDlgBox;
	}
	
	private TextBox nameField;
	public void setNameField(TextBox nameField) {
		this.nameField = nameField;
	}
	
	private Label errorLabel;
	public void setErrorLabel(Label errorLabel) {
		this.errorLabel = errorLabel;
	}

	public SendButton(String str) {
		super(str);
	}
	
	// Add a handler to send the name to the server
	public void setHandler() {
		MyHandler handler = new MyHandler();
		this.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}
	
	// Create a handler for the sendButton and nameField
	class MyHandler implements ClickHandler, KeyUpHandler {
		/** Fired when the user clicks on the sendButton. */
		public void onClick(ClickEvent event) {
			sendNameToServer();
		}

		/** Fired when the user types in the nameField. */
		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				sendNameToServer();
			}
		}

		/** Send the name from the nameField to the server and wait for a response. */
		private void sendNameToServer() {
			// First, we validate the input.
			errorLabel.setText("");
			String textToServer = nameField.getText();
			if (!FieldVerifier.isValidName(textToServer)) {
				errorLabel.setText("Please enter at least four characters");
				return;
			}

			// Then, we send the input to the server.
			SendButton.this.setEnabled(false);
			userInfoDlgBox.textToServerLabel.setText(textToServer);
			userInfoDlgBox.serverResponseLabel.setText("");
			GwtTest1.greetingService.greetServer(textToServer, new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					// Show the RPC error message to the user
					userInfoDlgBox.setText("Remote Procedure Call - Failure");
					userInfoDlgBox.serverResponseLabel.addStyleName("serverResponseLabelError");
					userInfoDlgBox.serverResponseLabel.setHTML(GwtTest1.SERVER_ERROR);
					userInfoDlgBox.center();
					userInfoDlgBox.closeButton.setFocus(true);
				}

				public void onSuccess(String result) {
					userInfoDlgBox.setText("Remote Procedure Call");
					userInfoDlgBox.serverResponseLabel.removeStyleName("serverResponseLabelError");
					userInfoDlgBox.serverResponseLabel.setHTML(result);
					userInfoDlgBox.center();
					userInfoDlgBox.closeButton.setFocus(true);
				}
			});
		}
	}
}
