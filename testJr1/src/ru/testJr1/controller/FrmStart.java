package ru.testJr1.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.testJr1.model.DataLists;
import ru.testJr1.model.Sqlite;
import ru.testJr1.model.entities.Adress;
import ru.testJr1.model.entities.Contract;
import ru.testJr1.model.entities.ContractFullView;
import ru.testJr1.model.entities.ContractTableView;
import ru.testJr1.model.entities.Person;
import ru.testJr1.model.entities.RealtyFactor;
import ru.testJr1.utils.HibernateUtil;
import ru.testJr1.viewer.WaitDlg;
import static ru.testJr1.viewer.Utils.stringToSqliteDate;

public class FrmStart {

	protected Shell shell;
	public Table tableBrowse;
	public static Display display;
	public static FrmStart window;	
	private Text txtTender;
	private Text txtBefore;
	private Text txtUntil;
	private Text txtOldFactor;
	private Text txtSquareFactor;
	private Text txtCalculateDate;
	private Text txtPrize;
	private Text txtContractNumber;
	private Text txtCreateDate;
	private Text txtFio;
	private Text txtBirthdate;
	private Text txtPassSerial;
	private Text txtPassNumber;
	private Text txtState;
	private Text txtIdx;
	private Text txtStatecount;
	private Text txtDistrict;
	private Text txtCity;
	private Text txtStreet;
	private Text txtBuilding;
	private Text txtCorp;
	private Text txtStructure;
	private Text txtHouse;
	private Text txtComment;
	private TableColumn tblclmnSerialNumber;
	private TableColumn tblclmnDateCreation;
	private TableColumn tblclmnInsurer;
	private TableColumn tblclmnPrize;
	private TableColumn tblclmnActualTime;
	private Combo comboRealtyFactor;

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

	private void openDb() {
		Thread task = new Thread () {
			private WaitDlg dlg;
			@Override
			public void run() {
				display.asyncExec(() -> {
					dlg = new WaitDlg(shell, SWT.APPLICATION_MODAL);
					dlg.open(); 
				});
				Sqlite.createDataBase();
				DataLists.loadDataFromDataBase();
				display.asyncExec(() -> {
					tableBrowse.removeAll();
				    for (ContractTableView contract : DataLists.contractTableViewList) {
						TableItem item = new TableItem(tableBrowse, 0);
						item.setText( new String[] {
								contract.getContract_id() + "",
								contract.getDate_of_creation(),
								contract.getFio(),
								contract.getPrize() + "",
								contract.getActual_time()
						});
				    }
				    comboRealtyFactor.removeAll();
				    for (RealtyFactor rf : DataLists.realtyFactorList) {
					    comboRealtyFactor.add(rf.getName());
				    }
				});
				display.asyncExec(() -> { dlg.close(); });
			}
		};
		task.start();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(814, 690);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(1, false));
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setSelection(0);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TabItem tbtmOne = new TabItem(tabFolder, SWT.NONE);
		tbtmOne.setText("One");
		
		Composite compositeOne = new Composite(tabFolder, SWT.NONE);
		tbtmOne.setControl(compositeOne);
		compositeOne.setLayout(new GridLayout(3, false));
		new Label(compositeOne, SWT.NONE);
		
		Button btnCreateContract = new Button(compositeOne, SWT.NONE);
		btnCreateContract.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ContractFullView contract = DataLists.contractTableFullList.get(0);
				txtTender.setText(contract.getTender() + "");
				txtBefore.setText(contract.getDate_of_creation());
				txtUntil.setText(contract.getDate_of_actual());
				comboRealtyFactor.setText(contract.getRealty_factor_name());
				txtOldFactor.setText(contract.getOld_year() + "");
				txtSquareFactor.setText(contract.getSquare() + "");
				txtCalculateDate.setText(contract.getDate_of_calculate());
				txtPrize.setText(contract.getPrize() + "");
				txtContractNumber.setText(contract.getContract_id() + "");
				txtCreateDate.setText(contract.getDate_of_creation1());
				txtFio.setText(contract.getFio());
				txtBirthdate.setText(contract.getBirth_date());
				txtPassSerial.setText(contract.getPassport_serial());
				txtPassNumber.setText(contract.getPassport_number());
				txtState.setText(contract.getState());
				txtIdx.setText(contract.getIdx());
				txtStatecount.setText(contract.getStatecount());
				txtDistrict.setText(contract.getDistrict());
				txtCity.setText(contract.getCity());
				txtStreet.setText(contract.getStreet());
				txtBuilding.setText(contract.getBuilding());
				txtCorp.setText(contract.getCorp());
				txtStructure.setText(contract.getStructure());
				txtHouse.setText(contract.getHouse());
				txtComment.setText(contract.getComment());
				tabFolder.setSelection(1);
			}
		});
		btnCreateContract.setText("Создать договор");
		
		Button btnOpenСontract = new Button(compositeOne, SWT.NONE);
		btnOpenСontract.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int contractNumber = 1;
				TableItem[] items = tableBrowse.getSelection();
				if (items != null && items.length > 0)
					contractNumber = Integer.parseInt(tableBrowse.getSelection()[0].getText(0));
				ContractFullView contract = DataLists.contractTableFullList.get(contractNumber - 1);
				txtTender.setText(contract.getTender() + "");
				txtBefore.setText(contract.getDate_of_creation());
				txtUntil.setText(contract.getDate_of_actual());
				comboRealtyFactor.setText(contract.getRealty_factor_name());
				txtOldFactor.setText(contract.getOld_year() + "");
				txtSquareFactor.setText(contract.getSquare() + "");
				txtCalculateDate.setText(contract.getDate_of_calculate());
				txtPrize.setText(contract.getPrize() + "");
				txtContractNumber.setText(contract.getContract_id() + "");
				txtCreateDate.setText(contract.getDate_of_creation1());
				txtFio.setText(contract.getFio());;
				txtBirthdate.setText(contract.getBirth_date());
				txtPassSerial.setText(contract.getPassport_serial());
				txtPassNumber.setText(contract.getPassport_number());
				txtState.setText(contract.getState());
				txtIdx.setText(contract.getIdx());
				txtStatecount.setText(contract.getStatecount());
				txtDistrict.setText(contract.getDistrict());
				txtCity.setText(contract.getCity());
				txtStreet.setText(contract.getStreet());
				txtBuilding.setText(contract.getBuilding());
				txtCorp.setText(contract.getCorp());
				txtStructure.setText(contract.getStructure());
				txtHouse.setText(contract.getHouse());
				txtComment.setText(contract.getComment());
				tabFolder.setSelection(1);
			}
		});
		btnOpenСontract.setText("Открыть контракт");
		
		tableBrowse = new Table(compositeOne, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_tableBrowse = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		gd_tableBrowse.widthHint = 819;
		tableBrowse.setLayoutData(gd_tableBrowse);
		tableBrowse.setHeaderVisible(true);
		tableBrowse.setLinesVisible(true);
		tableBrowse.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = compositeOne.getClientArea();
				Point preferredSize = tableBrowse.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2 * tableBrowse.getBorderWidth();
				if (preferredSize.y > area.height + tableBrowse.getHeaderHeight()) {
					Point vBarSize = tableBrowse.getVerticalBar().getSize();
					width -= vBarSize.x;
				}
				Point oldSize = tableBrowse.getSize();
				if (oldSize.x > area.width) {
					tblclmnSerialNumber.setWidth((int) (width * 0.10));
					tblclmnDateCreation.setWidth((int) (width * 0.15));
					tblclmnInsurer.setWidth((int) (width * 0.35));
					tblclmnPrize.setWidth((int) (width * 0.10));
					tblclmnActualTime.setWidth(width - tblclmnSerialNumber.getWidth() - 
							tblclmnDateCreation.getWidth() - tblclmnInsurer.getWidth() -
							tblclmnPrize.getWidth());
					tableBrowse.setSize(area.width, area.height);
				}
				else {
					tblclmnSerialNumber.setWidth((int) (width * 0.10));
					tblclmnDateCreation.setWidth((int) (width * 0.15));
					tblclmnInsurer.setWidth((int) (width * 0.35));
					tblclmnPrize.setWidth((int) (width * 0.10));
					tblclmnActualTime.setWidth(width - tblclmnSerialNumber.getWidth() - 
							tblclmnDateCreation.getWidth() - tblclmnInsurer.getWidth() -
							tblclmnPrize.getWidth());
				}
			}
		});
		
		tblclmnSerialNumber = new TableColumn(tableBrowse, SWT.NONE);
		tblclmnSerialNumber.setWidth(100);
		tblclmnSerialNumber.setText("Серия-Номер");
		
		tblclmnDateCreation = new TableColumn(tableBrowse, SWT.NONE);
		tblclmnDateCreation.setWidth(127);
		tblclmnDateCreation.setText("Дата заключения");
		
		tblclmnInsurer = new TableColumn(tableBrowse, SWT.NONE);
		tblclmnInsurer.setWidth(227);
		tblclmnInsurer.setText("Страхователь");
		
		tblclmnPrize = new TableColumn(tableBrowse, SWT.NONE);
		tblclmnPrize.setWidth(118);
		tblclmnPrize.setText("Премия");
		
		tblclmnActualTime = new TableColumn(tableBrowse, SWT.NONE);
		tblclmnActualTime.setWidth(189);
		tblclmnActualTime.setText("Срок действия");
		
		TabItem tbtmTwo = new TabItem(tabFolder, SWT.NONE);
		tbtmTwo.setText("Two");
		
		Composite compositeTwo = new Composite(tabFolder, SWT.NONE);
		tbtmTwo.setControl(compositeTwo);
		compositeTwo.setLayout(new GridLayout(1, false));
		
		Group grpCalculate = new Group(compositeTwo, SWT.NONE);
		grpCalculate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpCalculate.setText("Расчёт");
		grpCalculate.setLayout(new GridLayout(8, false));
		
		Label lblTender = new Label(grpCalculate, SWT.NONE);
		lblTender.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTender.setSize(95, 15);
		lblTender.setText("Страховая сумма");
		
		txtTender = new Text(grpCalculate, SWT.BORDER);
		GridData gd_txtTender = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtTender.widthHint = 116;
		txtTender.setLayoutData(gd_txtTender);
		txtTender.setSize(169, 21);
		
		Label lblActualtime = new Label(grpCalculate, SWT.NONE);
		lblActualtime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblActualtime.setSize(98, 15);
		lblActualtime.setText("Срок действия с   ");
		
		txtBefore = new Text(grpCalculate, SWT.BORDER);
		GridData gd_txtBefore = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtBefore.widthHint = 123;
		txtBefore.setLayoutData(gd_txtBefore);
		txtBefore.setSize(90, 21);
		
		Button btnCalendarbefore = new Button(grpCalculate, SWT.NONE);
		btnCalendarbefore.setText("...");
		
		Label lblUntil = new Label(grpCalculate, SWT.NONE);
		lblUntil.setSize(32, 15);
		lblUntil.setText("   по   ");
		
		txtUntil = new Text(grpCalculate, SWT.BORDER);
		GridData gd_txtUntil = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtUntil.widthHint = 131;
		txtUntil.setLayoutData(gd_txtUntil);
		txtUntil.setSize(90, 21);
		
		Button btnCalendarUntil = new Button(grpCalculate, SWT.NONE);
		btnCalendarUntil.setText("...");
		
		Label lblRealtyFactor = new Label(grpCalculate, SWT.NONE);
		lblRealtyFactor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRealtyFactor.setSize(106, 15);
		lblRealtyFactor.setText("Тип недвижимости");
		
		comboRealtyFactor = new Combo(grpCalculate, SWT.NONE);
		comboRealtyFactor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		comboRealtyFactor.setSize(169, 23);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		
		Label lblOldFactor = new Label(grpCalculate, SWT.NONE);
		lblOldFactor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOldFactor.setSize(81, 15);
		lblOldFactor.setText("Год постройки");
		
		txtOldFactor = new Text(grpCalculate, SWT.BORDER);
		txtOldFactor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		txtOldFactor.setSize(169, 21);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		
		Label lblSquareFactor = new Label(grpCalculate, SWT.NONE);
		lblSquareFactor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSquareFactor.setSize(52, 15);
		lblSquareFactor.setText("Площадь");
		
		txtSquareFactor = new Text(grpCalculate, SWT.BORDER);
		txtSquareFactor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		txtSquareFactor.setSize(169, 21);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		
		Button btnCalculate = new Button(grpCalculate, SWT.NONE);
		btnCalculate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCalculate.setSize(161, 25);
		btnCalculate.setText("Рассчитать");
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		
		Label lblCalculateDate = new Label(grpCalculate, SWT.NONE);
		lblCalculateDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCalculateDate.setSize(73, 15);
		lblCalculateDate.setText("Дата расчёта");
		
		txtCalculateDate = new Text(grpCalculate, SWT.BORDER);
		txtCalculateDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		txtCalculateDate.setSize(169, 21);
		
		Label lblPrize = new Label(grpCalculate, SWT.NONE);
		lblPrize.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPrize.setSize(25, 15);
		lblPrize.setText("Премия");
		
		txtPrize = new Text(grpCalculate, SWT.BORDER);
		txtPrize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		txtPrize.setSize(181, 21);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		new Label(grpCalculate, SWT.NONE);
		
		Composite composite = new Composite(compositeTwo, SWT.NONE);
		composite.setLayout(new GridLayout(9, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label lblContractnumber = new Label(composite, SWT.NONE);
		lblContractnumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblContractnumber.setText("Номер договора");
		
		txtContractNumber = new Text(composite, SWT.BORDER);
		txtContractNumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite, SWT.NONE);
		
		Label lblCreateDate = new Label(composite, SWT.NONE);
		lblCreateDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCreateDate.setText("Дата заключения");
		
		txtCreateDate = new Text(composite, SWT.BORDER);
		txtCreateDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnCalendarCreation = new Button(composite, SWT.NONE);
		btnCalendarCreation.setText("...");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblInsurer = new Label(composite, SWT.NONE);
		lblInsurer.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblInsurer.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblInsurer.setText("СТРАХОВАТЕЛЬ");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Button btnSelectinsurer = new Button(composite, SWT.NONE);
		btnSelectinsurer.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnSelectinsurer.setText("Выбрать");
		
		Label lblFio = new Label(composite, SWT.NONE);
		GridData gd_lblFio = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblFio.widthHint = 39;
		lblFio.setLayoutData(gd_lblFio);
		lblFio.setText("ФИО");
		
		txtFio = new Text(composite, SWT.BORDER);
		txtFio.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 5, 1));
		
		Button btnChange = new Button(composite, SWT.NONE);
		btnChange.setText("Изменить");
		new Label(composite, SWT.NONE);
		
		Label lblBithdate = new Label(composite, SWT.NONE);
		lblBithdate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblBithdate.setText("Дата рождения");
		
		txtBirthdate = new Text(composite, SWT.BORDER);
		GridData gd_txtBirthdate = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtBirthdate.widthHint = 98;
		txtBirthdate.setLayoutData(gd_txtBirthdate);
		
		Button btnCalendarbirthdate = new Button(composite, SWT.NONE);
		btnCalendarbirthdate.setText("...");
		
		Label lblPassSerial = new Label(composite, SWT.NONE);
		lblPassSerial.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPassSerial.setText("Паспорт   серия");
		
		txtPassSerial = new Text(composite, SWT.BORDER);
		GridData gd_txtPassSerial = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtPassSerial.widthHint = 105;
		txtPassSerial.setLayoutData(gd_txtPassSerial);
		
		Label lblPassNumber = new Label(composite, SWT.NONE);
		lblPassNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPassNumber.setText("номер");
		
		txtPassNumber = new Text(composite, SWT.BORDER);
		txtPassNumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Label lblAdress = new Label(composite, SWT.NONE);
		lblAdress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAdress.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblAdress.setText("Адрес недвижимости");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		txtState = new Text(composite, SWT.BORDER);
		txtState.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		txtIdx = new Text(composite, SWT.BORDER);
		txtIdx.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		txtStatecount = new Text(composite, SWT.BORDER);
		txtStatecount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		txtDistrict = new Text(composite, SWT.BORDER);
		txtDistrict.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		
		Label lblState = new Label(composite, SWT.NONE);
		lblState.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		lblState.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1));
		lblState.setText("государство");
		
		Label lblIdx = new Label(composite, SWT.NONE);
		lblIdx.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1));
		lblIdx.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		lblIdx.setText("индекс");
		new Label(composite, SWT.NONE);
		
		Label lblStatecount = new Label(composite, SWT.NONE);
		lblStatecount.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		lblStatecount.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 2, 1));
		lblStatecount.setText("республика, край, область");
		
		Label lblDistrict = new Label(composite, SWT.NONE);
		lblDistrict.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 4, 1));
		lblDistrict.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		lblDistrict.setText("район");
		
		txtCity = new Text(composite, SWT.BORDER);
		txtCity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		txtStreet = new Text(composite, SWT.BORDER);
		txtStreet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		
		txtBuilding = new Text(composite, SWT.BORDER);
		txtBuilding.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		txtCorp = new Text(composite, SWT.BORDER);
		txtCorp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		txtStructure = new Text(composite, SWT.BORDER);
		txtStructure.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		txtHouse = new Text(composite, SWT.BORDER);
		txtHouse.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblCity = new Label(composite, SWT.NONE);
		lblCity.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		lblCity.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1));
		lblCity.setText("населённый пункт");
		
		Label lblStreet = new Label(composite, SWT.NONE);
		lblStreet.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		lblStreet.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 4, 1));
		lblStreet.setText("улица");
		
		Label lblBuilding = new Label(composite, SWT.NONE);
		lblBuilding.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1));
		lblBuilding.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		lblBuilding.setText("дом");
		
		Label lblCorp = new Label(composite, SWT.NONE);
		lblCorp.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1));
		lblCorp.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		lblCorp.setText("корпус");
		
		Label lblStructure = new Label(composite, SWT.NONE);
		lblStructure.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1));
		lblStructure.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		lblStructure.setText("строение");
		
		Label lblHouse = new Label(composite, SWT.NONE);
		lblHouse.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		lblHouse.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1));
		lblHouse.setText("квартира");
		
		Label lblComment = new Label(composite, SWT.NONE);
		lblComment.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblComment.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblComment.setText("КОММЕНТАРИЙ");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblDescription = new Label(composite, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 2));
		lblDescription.setText("Комментарий к\r\n"
				+ "договору (не\r\n"
				+ "печатается на\r\n"
				+ "полисе)");
		
		txtComment = new Text(composite, SWT.BORDER);
		GridData gd_txtComment = new GridData(SWT.FILL, SWT.CENTER, true, false, 8, 2);
		gd_txtComment.heightHint = 46;
		txtComment.setLayoutData(gd_txtComment);
		new Label(composite, SWT.NONE);
		
		Button btnSave = new Button(composite, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Session session = HibernateUtil.getSessionFactory().openSession();
				Transaction transaction = null;
				try {
				    transaction = session.beginTransaction();
					
					Person newPerson = new Person(); 
					newPerson.setFio(txtFio.getText());
					newPerson.setBirth_date(stringToSqliteDate(txtBirthdate.getText()));
					newPerson.setPassport_serial(txtPassSerial.getText());
					newPerson.setPassport_number(txtPassNumber.getText());
					session.save(newPerson);
					
					Adress newAdress = new Adress();
					newAdress.setState(txtState.getText());
					newAdress.setIdx(txtIdx.getText());
					newAdress.setStatecount(txtStatecount.getText());
					newAdress.setDistrict(txtDistrict.getText());
					newAdress.setCity(txtCity.getText());
					newAdress.setStreet(txtStreet.getText());
					newAdress.setBuilding(txtBuilding.getText());
					newAdress.setCorp(txtCorp.getText());
					newAdress.setStructure(txtStructure.getText());
					newAdress.setHouse(txtHouse.getText());
					session.save(newAdress);
					
					RealtyFactor realtyFactor = DataLists.realtyFactorList.get(
							comboRealtyFactor.getSelectionIndex());
				    
				    Contract newContract = new Contract();
					newContract.setTender(Float.valueOf(txtTender.getText()));
					newContract.setCreate_date(stringToSqliteDate(txtBefore.getText()));
					newContract.setActual_date(stringToSqliteDate(txtUntil.getText()));
					newContract.setPrize(Float.valueOf(txtPrize.getText()));
					newContract.setOld_year(Integer.valueOf(txtOldFactor.getText()));
					newContract.setSquare(Integer.valueOf(txtSquareFactor.getText()));
					newContract.setCalculate_date(stringToSqliteDate(txtCalculateDate.getText()));
					newContract.setComment(txtComment.getText());
					newContract.setRealtyFactor(realtyFactor);
					newContract.setPerson(newPerson);
					newContract.setAdress(newAdress);
				    
					session.save(newContract);
					transaction.commit();
				}
				catch (RuntimeException e) {
				    if (transaction != null) {
				        transaction.rollback();
				    }
				    throw e; 
				}
				finally {
					session.close();
				}
				openDb();
			}
		});
		btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		btnSave.setText("Сохранить");
		
		Button btnBacktocontractlist = new Button(composite, SWT.NONE);
		btnBacktocontractlist.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				tabFolder.setSelection(0);
			}
		});
		btnBacktocontractlist.setText("К списку договоров");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
	}
}
