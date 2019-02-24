package ru.syrzhn.client.widgets;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import com.sencha.gxt.core.client.util.Margins;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;

public class TemplatePage implements IsWidget
{
	private    ContentPanel              panel     = null;
	private    VerticalLayoutContainer   vlc       = null;

	protected  HorizontalLayoutContainer hlcHeader = null;
	protected  HorizontalLayoutContainer hlcBody   = null;
	protected  HorizontalLayoutContainer hlcFooter = null;

	protected  HorizontalPanel           hpLeft    = null; 
	protected  HorizontalPanel           hpCenter  = null; 
	protected  HorizontalPanel           hpRight   = null; 
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public TemplatePage()
	{
		panel = new ContentPanel();
		panel.setBodyBorder   (false);
		panel.setBorders      (false);
		panel.setHeaderVisible(false);
		panel.setPixelSize (400, 180);
	   
		vlc = new VerticalLayoutContainer();
		vlc.setBorders(true);
		vlc.getElement().getStyle().setBackgroundColor("white"); 

		createHeaderPanel();
		createBodyPanel  ();
		createFooterPanel();
		
		Margins margins = new Margins(2, 2, 0, 2);
		vlc.add(hlcHeader, new VerticalLayoutData( 1,-1, margins));
		vlc.add(hlcBody, new VerticalLayoutData( 1, 1, margins));
		margins = new Margins(2, 2, 2, 2);
		vlc.add(hlcFooter, new VerticalLayoutData( 1,-1, margins));
		panel.add(vlc);
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	protected void createHeaderPanel()
	{
		hlcHeader = new HorizontalLayoutContainer();
		hlcHeader.setHeight(24);
		hlcHeader.setBorders(true);
		hlcHeader.getElement().getStyle().setBackgroundColor("yellow"); 
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	protected void createBodyPanel()
	{
		hlcBody = new HorizontalLayoutContainer();
		hlcBody.setBorders(true);
		
		hpLeft   = new HorizontalPanel(); 
		hpCenter = new HorizontalPanel(); 
		hpRight  = new HorizontalPanel(); 
		
		hpLeft  .setWidth("75px");
		hpRight .setWidth("120px");
		
		Margins margins = new Margins(0, 2, 0, 2);
		hlcBody.add(hpLeft  , new HorizontalLayoutData(-1, 1));
		hlcBody.add(hpCenter, new HorizontalLayoutData( 1, 1, margins));
		hlcBody.add(hpRight , new HorizontalLayoutData(-1, 1));
		
		hpLeft  .getElement().getStyle().setBackgroundColor("#CCC");
		hpCenter.getElement().getStyle().setBackgroundColor("#EEE");
		hpRight .getElement().getStyle().setBackgroundColor("#CCC");
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	protected void createFooterPanel()
	{
		hlcFooter = new HorizontalLayoutContainer();
		hlcFooter.setHeight(32);
		hlcFooter.setBorders(true);
		hlcFooter.getElement().getStyle().setBackgroundColor("cyan"); 
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	@Override
	public Widget asWidget()
	{
		return panel;
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
