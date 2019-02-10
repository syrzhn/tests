package ru.testJr1;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;

public class FrmStart {

	protected Shell shell;
	public Table tableBrowse;
	public static  Display display;
	public static FrmStart window;
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			window = new FrmStart();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		openDb();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	/**
	 * Открывает БД в отдельном потоке
	 */
	private void openDb() {
		Thread task = new Thread () {
			private WaitDlg dlg;
			@Override
			public void run() {
				display.asyncExec(() -> {
					dlg = new WaitDlg(shell, SWT.APPLICATION_MODAL);
					dlg.open(); 
				});
				Sqlite.browseContractTableView();
				display.asyncExec(() -> {dlg.shell.dispose(); });
			}
		};
		task.start();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(864, 378);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(1, false));
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TabItem tbtmOne = new TabItem(tabFolder, SWT.NONE);
		tbtmOne.setText("One");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmOne.setControl(composite);
		composite.setLayout(new GridLayout(2, false));
		
		tableBrowse = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_tableBrowse = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tableBrowse.widthHint = 819;
		tableBrowse.setLayoutData(gd_tableBrowse);
		tableBrowse.setHeaderVisible(true);
		tableBrowse.setLinesVisible(true);
		
		TableColumn tblclmnSerialNumber = new TableColumn(tableBrowse, SWT.NONE);
		tblclmnSerialNumber.setWidth(100);
		tblclmnSerialNumber.setText("Серия-Номер");
		
		TableColumn tblclmnDateCreation = new TableColumn(tableBrowse, SWT.NONE);
		tblclmnDateCreation.setWidth(127);
		tblclmnDateCreation.setText("Дата заключения");
		
		TableColumn tblclmnInsurer = new TableColumn(tableBrowse, SWT.NONE);
		tblclmnInsurer.setWidth(227);
		tblclmnInsurer.setText("Страхователь");
		
		TableColumn tblclmnPrize = new TableColumn(tableBrowse, SWT.NONE);
		tblclmnPrize.setWidth(136);
		tblclmnPrize.setText("Премия");
		
		TableColumn tblclmnActualDate = new TableColumn(tableBrowse, SWT.NONE);
		tblclmnActualDate.setWidth(118);
		tblclmnActualDate.setText("Дата окончания");
	}
	
	class WaitDlg extends Dialog {
		private Shell shell;
		private Label lblWait;
		public WaitDlg(Shell parent, int style) {
			super(parent, style);
			setText("Ждите...");
		}
		public void open() {
			createContents();
			shell.open();
			shell.layout();
			Display display = getParent().getDisplay();
			final Runnable timer = new Runnable() {
				public void run() {
					if (shell.isDisposed()) return;
					String s = lblWait.getText();
					if (s.equals(".  ")) {
						lblWait.setText(".. ");
						s = lblWait.getText();
					}
					else if (s.equals(".. ")) {
						lblWait.setText("...");
						s = lblWait.getText();
					}				
					else if (s.equals("...")) {
						lblWait.setText(" ..");
						s = lblWait.getText();
					}				
					else if (s.equals(" ..")) {
						lblWait.setText("  .");
						s = lblWait.getText();
					}				
					else if (s.equals("  .")) {
						lblWait.setText(".  ");
						s = lblWait.getText();
					}				
					display.timerExec(300, this);
				}
			};
			display.timerExec(300, timer);
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
			lblWait.setText(".  ");
			lblWait.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
			lblWait.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		}
	}
}
