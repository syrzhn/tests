package ru.syrzhn.client.dialogs;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UserInfoDialog extends DialogBox {
	public final Button closeButton;
	public final Label textToServerLabel;
	public final HTML serverResponseLabel;
	private final VerticalPanel dialogVPanel;
	private final Button sendButton;
	public UserInfoDialog(final Button button) {
		super();
		closeButton = new Button("Close");
		textToServerLabel = new Label();
		serverResponseLabel = new HTML();
		sendButton = button;
		
		dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		this.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				UserInfoDialog.this.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});
	}
}
