package ru.syrzhn.test1r.viewer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class WaitDlg extends Dialog {
	private Shell shell;
	private Label lblWait;
	public WaitDlg(Shell parent, int style) {
		super(parent, style);
		setText("Wait...");
	}
	public void open() {
		createContents();
		shell.open();
		shell.layout();
		final Display display = getParent().getDisplay();
		final int time = 306;
		final Runnable points = new Runnable() {
			public void run() {
				if (shell.isDisposed()) return;
				String s = lblWait.getText();
				if (s.equals(".   ")) {
					lblWait.setText("..  ");
					s = lblWait.getText();
				}
				else if (s.equals("..  ")) {
					lblWait.setText("... ");
					s = lblWait.getText();
				}				
				else if (s.equals("... ")) {
					lblWait.setText(" ...");
					s = lblWait.getText();
				}				
				else if (s.equals(" ...")) {
					lblWait.setText("  ..");
					s = lblWait.getText();
				}				
				else if (s.equals("  ..")) {
					lblWait.setText("   .");
					s = lblWait.getText();
				}				
				else if (s.equals("   .")) {
					lblWait.setText(".   ");
					s = lblWait.getText();
				}				
				display.timerExec(time, this);
			}
		};
		display.timerExec(time, points);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	private void createContents() {
		Shell parentShell = getParent();
		shell = new Shell(parentShell, SWT.BORDER | SWT.TITLE | SWT.APPLICATION_MODAL);
		shell.setSize(180, 77);
		Point p = parentShell.getLocation();
		shell.setLocation(p.x + parentShell.getSize().x / 2 - shell.getSize().x, 
				p.y + parentShell.getSize().y / 2 - shell.getSize().y);
		shell.setText(getText());
		shell.setLayout(new GridLayout(1, false));
		lblWait = new Label(shell, SWT.NONE);
		lblWait.setText(".   ");
		lblWait.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		lblWait.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
	}
	public void close() { shell.dispose(); }
}
