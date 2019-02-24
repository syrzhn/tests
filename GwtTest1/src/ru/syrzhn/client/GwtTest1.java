package ru.syrzhn.client;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;

import ru.syrzhn.client.dialogs.UserInfoDialog;
import ru.syrzhn.client.widgets.SendButton;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtTest1 implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	public static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	public final static GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final SendButton sendButton = new SendButton("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		
		final Label errorLabel = new Label();

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final UserInfoDialog userInfoDlgBox = new UserInfoDialog(sendButton);
		sendButton.setUserInfoDlgBox(userInfoDlgBox);
		// We can add style names to widgets
		sendButton.addStyleName("sendButton");
		sendButton.setErrorLabel(errorLabel);
		sendButton.setNameField(nameField);
		
		sendButton.setHandler();

		// Create a CellTable.
		CellTable<Contact> table = new CellTable<Contact>();
		// Create name column.
		TextColumn<Contact> nameColumn = new TextColumn<Contact>() {
			@Override
			public String getValue(Contact contact) {
				return contact.name;
			}
		};
		// Create address column.
		TextColumn<Contact> addressColumn = new TextColumn<Contact>() {
			@Override
			public String getValue(Contact contact) {
				return contact.address;
			}
		};
		// Add the columns.
		table.addColumn(nameColumn, "Name");
		table.addColumn(addressColumn, "Address");
		// Set the width of the table and put the table in fixed width mode.
		table.setWidth("100%", true);
		// Set the width of each column.
		table.setColumnWidth(nameColumn, 35.0, Unit.PCT);
		table.setColumnWidth(addressColumn, 65.0, Unit.PCT);
		// Set the total row count. This isn't strictly necessary, but it affects
		// paging calculations, so its good habit to keep the row count up to date.
		table.setRowCount(CONTACTS.size(), true);
		// Push the data into the widget.
		table.setRowData(0, CONTACTS);
		// Add it to the root panel.
		RootPanel.get().add(table);

		TabPanel tabs = new TabPanel();
		tabs.add(new HTML("1"), "Basic Panels");
		tabs.add(new HTML("2"), "Dock Panel");
		tabs.add(table, "Tables");
		tabs.setWidth("100%");
		tabs.selectTab(0);
		RootPanel.get().add(tabs);
	}

	private static class Contact {
		private final String address;
		private final String name;

		public Contact(String name, String address) {
			this.name = name;
			this.address = address;
		}
	}

	// The list of data to display.
	private static List<Contact> CONTACTS = Arrays.asList(new Contact("John", "123 Fourth Road"),
			new Contact("Mary", "222 Lancer Lane"));
}
