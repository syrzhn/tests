package ru.syrzhn.client.widgets;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("deprecation")
public class HyperLynk 
{
	private Hyperlink hyperlink = null;
	public HyperLynk(final String url)
	{
		hyperlink = new Hyperlink(url, "");
		
		hyperlink.addClickListener( new ClickListener(){
			@Override
			public void onClick(Widget sender) {
		        final JavaScriptObject window = newWindow(url, "", "");
//		        setWindowTarget(window, URL);
		        new AsyncCallback<Object>() {
		           public void onSuccess(Object o)  {
		                setWindowTarget(window, url);
		           }
		           @Override
		           public void onFailure(Throwable caught) {
		           }
		        };            
			}
		});
		
	}
	public Hyperlink getHyperlink()
	{
		return hyperlink;
	}

	/**
	 * Процедуры создания новой вкладки с заданным URL
	 * @param url адрес страницы в интернете
	 * @param name наименование
	 * @param features свойства
	 * @return вкладка браузера
	 */
	private static native JavaScriptObject newWindow(String url, String name, String features)/*-{
    	var window = $wnd.open(url, name, features);
    	return window;
    }-*/;

	/**
	 * Процедуры открытия новой вкладки с заданным URL
	 * @param window окно браузера
	 * @param url адрес страницы
	 */
	private static native void setWindowTarget(JavaScriptObject window, String url)/*-{
        window.location = target;
	}-*/;	
}
