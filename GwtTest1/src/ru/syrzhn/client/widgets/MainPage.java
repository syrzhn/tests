package ru.syrzhn.client.widgets;

import com.sencha.gxt.core.client.util.Margins;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

public class MainPage extends TemplatePage
{
	final String URL = "https://www.sencha.com";

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public MainPage() 
	{
		super();
		createBody();
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private void createBody()
	{
		HorizontalPanel hp = new HorizontalPanel(); 
		Hyperlink link = new HyperLynk (URL).getHyperlink();
		hp.add(link);

		VerticalLayoutContainer vCenter = new VerticalLayoutContainer();
		hpCenter.add(vCenter);
		Margins margins = new Margins(10, 0, 0, 5);
		
		vCenter.add(hp, new VerticalLayoutData( 1, -1, margins));
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
