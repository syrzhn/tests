package ru.klinichev.firstproject.client;

import ru.klinichev.firstproject.shared.FieldVerifier;
import ru.klinichev.firstproject.shared.Person;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FirstProject implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	private final PersonServiceAsync personService = GWT.create(PersonService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button sendButton = new Button("Send");
		final TextBox firstNameField = new TextBox();
		firstNameField.getElement().setAttribute("placeholder", "First name");
		final TextBox lastNameField = new TextBox();
		lastNameField.getElement().setAttribute("placeholder", "Last name");
		final Label errorLabel = new Label();
		final FlexTable table = new FlexTable();

		sendButton.addStyleName("sendButton");
		table.addStyleName("flexTable");

		RootPanel.get("firstNameFieldContainer").add(firstNameField);
		RootPanel.get("lastNameFieldContainer").add(lastNameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		RootPanel.get("tableContainer").add(table);

		firstNameField.setFocus(true);

		class MyHandler implements ClickHandler, KeyUpHandler {

			private void sendNameToServer() {
				errorLabel.setText("");
				String firstName = firstNameField.getText();
				if (!FieldVerifier.isValidName(firstName)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}
				String lastName = lastNameField.getText();
				if (!FieldVerifier.isValidName(lastName)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				Person person = new Person();
				person.setFirstName(firstName);
				person.setLastName(lastName);
				personService.insert(person, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable arg0) {
						errorLabel.setText("Failed to send this entry to the database");
					}

					@Override
					public void onSuccess(Void arg0) {
						errorLabel.setText("Refresh to see the changes in the database");
					}

				});

			}

			@Override
			public void onClick(ClickEvent arg0) {
				sendNameToServer();
			}

			@Override
			public void onKeyUp(KeyUpEvent arg0) {
				if (arg0.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}
		}

		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		lastNameField.addKeyUpHandler(handler);

		personService.getAllPersons(new AsyncCallback<List<Person>>() {

			@Override
			public void onFailure(Throwable arg0) {
				errorLabel.setText("Failed to load database");
			}

			@Override
			public void onSuccess(List<Person> arg0) {
				fillTable(arg0, table);
			}

		});

	}
  
	public void fillTable(List<Person> persons, FlexTable table) {
		int count = 0;
		for (Person p : persons) {
			table.setText(count, 0, p.getId().toString());
			table.setText(count, 1, p.getFirstName());
			table.setText(count, 2, p.getLastName());
			count++;
		}
	}
  
}
