/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ui;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import au.com.noojee.battlefieldjava.config.Loader;
import au.com.noojee.battlefieldjava.engine.GameSettings;
import au.com.noojee.battlefieldjava.engine.Ruler;
import au.com.noojee.battlefieldjava.engine.RulerType;
import au.com.noojee.battlefieldjava.engine.TurnListener;
import au.com.noojee.battlefieldjava.engine.UserCancelledBattle;
import au.com.noojee.battlefieldjava.engine.World;
import au.com.noojee.battlefieldjava.images.ImageLoader;
import au.com.noojee.battlefieldjava.occupants.Castle;
import au.com.noojee.battlefieldjava.ruler.IRuler;




/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BattlefieldJava extends JFrame implements TurnListener, MRUMenu.listener, FileManager.Listener
{
	private static final int PANEL_WIDTH = 220;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3392262148564852470L;
	public static String IMAGE_ROOT = "net\\sf\\battlefieldjava\\ui\\images";
	private javax.swing.JMenuBar	jJMenuBar		= null;
	private javax.swing.JMenu		fileMenu		= null;
	private javax.swing.JMenu		helpMenu		= null;
	private javax.swing.JMenuItem	exitMenuItem	= null;
	private javax.swing.JMenuItem	aboutMenuItem	= null;
	private JPanel					jControl		= null;
	private BattleField				battleField		= null;
	private FileManager				fileManager 	= null;
	private MRUMenu					mruMenu			= null;

	private JButton					jRunButton		= null;
	private JButton					jStepButton		= null;
	private JButton					jPauseButton	= null;
	private JButton					jStopButton		= null;

	private boolean					bCancelled;
	private boolean					stepMode;
	// If true then the battle simulation is running.
	private boolean					running			= false;

	private JPanel					jBattlePanel	= null;
	private JProgressBar			jProgressBar	= null;

	private JCheckBox				jShowIDs		= null;
	private JMenuItem				jmniSettings	= null;
	private JTabbedPane				jScoreTabs		= null;

	private JMenu					viewMenu		= null;
	private EventLogWindow			EventLog		= null;
	private JPanel					jContent		= null;
	private JTabbedPane				jTabbedPane		= null;

	private JMenuItem jmniSave = null;
	private JMenuItem jmniOpen = null;
	private JMenuItem jmiNew = null;
	private JMenuItem jmniSaveAs = null;
	private static final int	VERSION	= 1;
	
	
	public BattlefieldJava()
	{
		super();
		initialize();
		mruMenu = new MRUMenu(fileMenu, 5, this);
		fileManager = new FileManager(this, mruMenu, "Untitled", "Battle Field Java", "bfj");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setIconImage(ImageLoader.getImage(Castle.IMAGE_PATH));
		try
		{
			Loader.getInstance();
		}
		catch (Exception e)
		{
			Utilities.showException(this, e, false);
		}

		this.setContentPane(getJContent());
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(getJJMenuBar());
		this.setSize(760, 607);
		this.setTitle("Battlefield Java");
		World.init(battleField);
		World.setTurnListener(this);
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getControlPanel()
	{
		if (jControl == null)
		{
			jControl = new JPanel();
			jControl.setLayout(new BorderLayout());
			JPanel controls = new JPanel();
			controls.setLayout(null);
			controls.setPreferredSize(new java.awt.Dimension(PANEL_WIDTH, 180));
			controls.add(getJRunButton(), null);
			controls.add(getJStepButton(), null);
			controls.add(getJPauseButton(), null);
			controls.add(getJStopButton(), null);
			controls.add(getJShowIDs(), null);
			jControl.add(controls, BorderLayout.NORTH);
			getJScoreTabs().setPreferredSize(new java.awt.Dimension(PANEL_WIDTH, 600));
			jControl.add(getJScoreTabs(), BorderLayout.CENTER);
			
			
//			jControl.setMinimumSize(new java.awt.Dimension(PANEL_WIDTH, 100));
//			jControl.setMaximumSize(new java.awt.Dimension(PANEL_WIDTH, 100));
		}
		return jControl;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private BattleField getBattleFieldPanel()
	{
		if (battleField == null)
		{
			battleField = new BattleField(GameSettings.getWIDTH(), GameSettings.getHEIGHT());
		}
		return battleField;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJRunButton()
	{
		if (jRunButton == null)
		{
			jRunButton = new JButton();
			jRunButton.setBounds(20, 20, 96, 22);
			jRunButton.setText("Run");
			jRunButton.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					BattlefieldJava.this.doRun();
				}
			});
		}
		return jRunButton;
	}

	/**
	 * 
	 */
	protected synchronized void doRun()
	{
		boolean loaded = true;
		if (!running)
			loaded = loadRulers();
		
		if (loaded)
		{
			stepMode = false;
			bCancelled = false;
			getJStepButton().setEnabled(false);
			getJRunButton().setEnabled(false);
			getJPauseButton().setEnabled(true);
			getJStopButton().setEnabled(true);

			if (!running)
			{
				World.getInstance().setShowIDs(jShowIDs.isSelected());
				World.getInstance().populate();
				new Thread(World.getInstance(), "World").start();
			}
			else
				notify();
		}

	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJStepButton()
	{
		if (jStepButton == null)
		{
			jStepButton = new JButton();
			jStepButton.setBounds(20, 70, 96, 22);
			jStepButton.setText("Step");
			jStepButton.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					BattlefieldJava.this.doStep();
				}
			});
		}
		return jStepButton;
	}

	/**
	 * 
	 */
	protected synchronized void doStep()
	{
		getJStepButton().setEnabled(false);
		getJRunButton().setEnabled(false);
		getJPauseButton().setEnabled(true);
		getJStopButton().setEnabled(true);

		stepMode = true;
		bCancelled = false;

		World.getInstance().setShowIDs(jShowIDs.isSelected());
		if (!running)
		{
			if (loadRulers())
			{
				new Thread(World.getInstance()).start();
			}
		}
		else
			notify();
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJPauseButton()
	{
		if (jPauseButton == null)
		{
			jPauseButton = new JButton();
			jPauseButton.setBounds(20, 45, 96, 22);
			jPauseButton.setText("Pause");
			jPauseButton.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					BattlefieldJava.this.doPause();
				}
			});
		}
		return jPauseButton;
	}

	/**
	 * 
	 */
	protected synchronized void doPause()
	{
		stepMode = true;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJStopButton()
	{
		if (jStopButton == null)
		{
			jStopButton = new JButton();
			jStopButton.setBounds(20, 95, 96, 22);
			jStopButton.setText("Stop");
			jStopButton.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					BattlefieldJava.this.doStop();
				}
			});
		}
		return jStopButton;
	}

	/**
	 * 
	 */
	protected synchronized void doStop()
	{
		bCancelled = true;
		getJStepButton().setEnabled(true);
		getJRunButton().setEnabled(true);
		getJStopButton().setEnabled(false);
		getJPauseButton().setEnabled(false);
		notify();
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getBattlePanel()
	{
		if (jBattlePanel == null)
		{
			jBattlePanel = new JPanel();
			jBattlePanel.setLayout(new BorderLayout());
			jBattlePanel.add(getBattleFieldPanel(), java.awt.BorderLayout.CENTER);
			jBattlePanel.add(getJProgressBar(), java.awt.BorderLayout.SOUTH);
		}
		return jBattlePanel;
	}

	/**
	 * This method initializes jProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar()
	{
		if (jProgressBar == null)
		{
			jProgressBar = new JProgressBar(0, GameSettings.getRounds());
			jProgressBar.setValue(0);
			jProgressBar.setStringPainted(true);
		}
		return jProgressBar;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJShowIDs()
	{
		if (jShowIDs == null)
		{
			jShowIDs = new JCheckBox();
			jShowIDs.setBounds(22, 135, 97, 21);
			jShowIDs.setText("Show IDs");
		}
		return jShowIDs;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJmniSettings()
	{
		if (jmniSettings == null)
		{
			jmniSettings = new JMenuItem();
			jmniSettings.setText("Settings");
			jmniSettings.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{

					SettingsDialog dialog = new SettingsDialog(BattlefieldJava.this);
					dialog.setVisible(true);
					if (dialog.getResult() == SettingsDialog.OK)
					{
						World.getInstance().reset();
					}
				}
			});
		}
		return jmniSettings;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJScoreTabs()
	{
		if (jScoreTabs == null)
		{
			jScoreTabs = new JTabbedPane();
//			jScoreTabs.setBounds(8, 176, 134, 500);
			//jScoreTabs.setAutoscrolls(true);
		}
		return jScoreTabs;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getViewMenu()
	{
		if (viewMenu == null)
		{
			viewMenu = new JMenu();
			viewMenu.setText("View");
			viewMenu.add(getJmniSettings());
		}
		return viewMenu;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private EventLogWindow getEventLog()
	{
		if (EventLog == null)
		{
			EventLog = new EventLogWindow();
			EventLog.setPreferredSize(new java.awt.Dimension(150, 600));
		}
		return EventLog;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContent()
	{
		if (jContent == null)
		{
			jContent = new JPanel();
			jContent.setLayout(new BorderLayout());
			
			JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			splitter.add(getLeftPanel());
			splitter.add(getBattlePanel());
			jContent.add(splitter, java.awt.BorderLayout.CENTER);

		}
		return jContent;
	}

	private JTabbedPane getLeftPanel()
	{
		if (jTabbedPane == null)
		{
			jTabbedPane = new JTabbedPane();

			jTabbedPane.addTab("Control", null, getControlPanel(), null);
			jTabbedPane.addTab("EventLog", null, getEventLog(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getJmniSave() {
		if (jmniSave == null) {
			jmniSave = new JMenuItem();
			jmniSave.setText("Save");
			jmniSave.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return jmniSave;
	}
	/**
	 * This method initializes jMenuItem1	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getJmniOpen() {
		if (jmniOpen == null) {
			jmniOpen = new JMenuItem();
			jmniOpen.setText("Open");
			jmniOpen.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return jmniOpen;
	}
	/**
	 * This method initializes jMenuItem2	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getJmiNew() {
		if (jmiNew == null) {
			jmiNew = new JMenuItem();
			jmiNew.setText("New");
			jmiNew.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return jmiNew;
	}
	/**
	 * This method initializes jMenuItem3	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getJmniSaveAs() {
		if (jmniSaveAs == null) {
			jmniSaveAs = new JMenuItem();
			jmniSaveAs.setText("Save As");
			jmniSaveAs.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return jmniSaveAs;
	}
	
	
    public static void main(String[] args)
	{
		BattlefieldJava application = new BattlefieldJava();
		application.setVisible(true);
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private javax.swing.JMenuBar getJJMenuBar()
	{
		if (jJMenuBar == null)
		{
			jJMenuBar = new javax.swing.JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getViewMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private javax.swing.JMenu getFileMenu()
	{
		if (fileMenu == null)
		{
			fileMenu = new javax.swing.JMenu();
			fileMenu.setText("File");
			fileMenu.add(getJmiNew());
			fileMenu.add(getJmniOpen());
			fileMenu.add(getJmniSave());
			fileMenu.add(getJmniSaveAs());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private javax.swing.JMenu getHelpMenu()
	{
		if (helpMenu == null)
		{
			helpMenu = new javax.swing.JMenu();
			helpMenu.setText("Help");
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private javax.swing.JMenuItem getExitMenuItem()
	{
		if (exitMenuItem == null)
		{
			exitMenuItem = new javax.swing.JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private javax.swing.JMenuItem getAboutMenuItem()
	{
		if (aboutMenuItem == null)
		{
			aboutMenuItem = new javax.swing.JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					new AboutDialog(BattlefieldJava.this).setVisible(true);
				}
			});
		}
		return aboutMenuItem;
	}

	boolean loadRulers()
	{
		boolean ok = false;
		SelectRulersDialog dialog = new SelectRulersDialog(this);
		World.getInstance().reset();
		dialog.setVisible(true);
		EventLogWindow.getInstance().reset();
		RulerType[] rulerTypes = dialog.getSelectedRulerTypes();
		jScoreTabs.removeAll();
		for (int i = 0; i < rulerTypes.length; i++)
		{
			try
			{
				Ruler ruler = rulerTypes[i].newInstance();
				World.getInstance().addRuler(ruler);
				this.battleField.refresh();
				jScoreTabs.addTab(ruler.getRulerName()
						, ruler.getImageIcon(this)
						, new RulerScorePanel(ruler)
						, ruler.getSchoolName() + ":" + ruler.getRulerName());
				ok = true;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ok;
	}

	/* (non-Javadoc)
	 * @see openruler.engine.TurnListener#startBattle()
	 */
	public synchronized void startBattle() throws UserCancelledBattle
	{
		running = true;

		SwingUtilities.invokeLater(new Runnable()
		{

			public void run()
			{

				jProgressBar.setMaximum(GameSettings.getRounds());
				jProgressBar.setValue(0);
				jProgressBar.setStringPainted(true);
				getJmniSettings().setEnabled(false);
			}
		});

		if (stepMode)
			try
			{
				SwingUtilities.invokeLater(new Runnable()
				{

					public void run()
					{

						getJStepButton().setEnabled(true);
						getJRunButton().setEnabled(true);
						getJPauseButton().setEnabled(false);
						getJStopButton().setEnabled(true);
					}
				});

				wait();
				if (bCancelled)
				{
					throw new UserCancelledBattle();
				}
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	/* (non-Javadoc)
	 * @see openruler.engine.TurnListener#startTurn(int)
	 */
	public synchronized void startRound(int turn) throws UserCancelledBattle
	{
		final int aturn = turn;
		SwingUtilities.invokeLater(new Runnable()
		{

			public void run()
			{

				jProgressBar.setValue(aturn);
				jProgressBar.setString(Integer.toString(aturn));
			}
		});
	}

	/* (non-Javadoc)
	 * @see openruler.engine.TurnListener#startRulerTurn(int, openruler.ruler.IRuler)
	 */
	public synchronized void startTurn(int turn, IRuler ruler) throws UserCancelledBattle
	{
		// No action required
	}

	/* (non-Javadoc)
	 * @see openruler.engine.TurnListener#endRulerTurn(int, openruler.ruler.IRuler)
	 */
	public synchronized void endTurn(int turn, IRuler ruler) throws UserCancelledBattle
	{
		final IRuler localRuler = ruler;
		if (stepMode == true)
			try
			{
				SwingUtilities.invokeLater(new Runnable()
				{

					public void run()
					{
						getJStepButton().setEnabled(true);
						getJRunButton().setEnabled(true);
						getJPauseButton().setEnabled(false);
						getJStopButton().setEnabled(true);
					}
				});

				wait();
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		updateScore(localRuler);

		if (bCancelled)
			throw new UserCancelledBattle();
	}

	/* (non-Javadoc)
	 * @see openruler.engine.TurnListener#endTurn(openruler.ruler.IRuler)
	 */
	public synchronized void endRound(int turn) throws UserCancelledBattle
	{
		// No action required
	}

	/* (non-Javadoc)
	 * @see openruler.engine.TurnListener#endBattle()
	 */
	public synchronized void endBattle(boolean cancelled)
	{
		running = false;
		SwingUtilities.invokeLater(new Runnable()
		{

			public void run()
			{
				getJStepButton().setEnabled(true);
				getJRunButton().setEnabled(true);
				getJPauseButton().setEnabled(false);
				getJStopButton().setEnabled(false);
				getJmniSettings().setEnabled(true);
			}
		});
		if (!cancelled)
		{
			SwingUtilities.invokeLater(new Runnable()
			{

				public void run()
				{
					jProgressBar.setValue(GameSettings.getRounds());
					jProgressBar.setString(Integer.toString(GameSettings.getRounds()));
				}
			});
		}
		else
			SwingUtilities.invokeLater(new Runnable()
			{

				public void run()
				{
					jProgressBar.setString("Cancelled");
				}
			});
	}

	// Update the score for the given ruler.
	void updateScore(IRuler ruler)
	{
		final IRuler localRuler = ruler;
		SwingUtilities.invokeLater(new Runnable()
		{

			public void run()
			{

				// find the ruler tab and update it.

				for (int i = 0; i < getJScoreTabs().getTabCount(); i++)
				{
					RulerScorePanel score = (RulerScorePanel) getJScoreTabs().getComponentAt(i);
					if (score.getRuler() == localRuler)
					{
						score.updateScore(((Ruler) localRuler).getScore());
					}
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see openruler.ui.MRUMenu.listener#onMRUSelect(java.io.File)
	 */
	public void onMRUSelect(File filSelected)
	{
		try
		{
			fileManager.load(filSelected);
		}
		catch (Exception e)
		{
			Utilities.showException(this, e, false);
		}
		
	}

	/* (non-Javadoc)
	 * @see openruler.ui.FileManager.Listener#load(java.io.File)
	 */
	public void load(File file) throws FileNotFoundException, IOException, FileManager.Exception
	{
		FileInputStream out = new FileInputStream(file);
		ObjectInputStream s = new ObjectInputStream(out);

		// read the version
		s.readInt();

		World.getInstance().reset();
		EventLogWindow.getInstance().reset();
		jScoreTabs.removeAll();

		// load the settings
		GameSettings.readObject(s);
		
		// load the players
		int rulers = s.readInt();
		for (int i = 0; i < rulers; i++)
		{
			try
			{
				Ruler ruler = (Ruler)s.readObject();
				World.getInstance().addRuler(ruler);
				this.battleField.refresh();
				jScoreTabs.addTab(ruler.getRulerName()
						, ruler.getImageIcon(this)
						, new RulerScorePanel(ruler)
						, ruler.getSchoolName() + ":" + ruler.getRulerName());
				//Score score = (Score)s.readObject();
//				ruler.resetScore(score);

			}
			catch (ClassNotFoundException e)
			{
				throw new FileManager.Exception("Load failed", e);
			}
		
		}

		// load the grid
//		World.getInstance().resetGrid(s.readObject());
		World.getInstance().setCurrentRound(s.readInt());
		World.getInstance().setCurrentTurnRuler(s.readInt());
		
		
		
	}

	/* (non-Javadoc)
	 * @see openruler.ui.FileManager.Listener#save(java.io.File)
	 */
	public void save(File file) throws FileNotFoundException, IOException, FileManager.Exception
	{
		FileOutputStream out = new FileOutputStream(file);
		ObjectOutputStream s = new ObjectOutputStream(out);
		
		s.write(VERSION);
		
		// save the settings
		GameSettings.writeObject(s);
		
		// save the players
		int currentTurnRuler = -1;
		Ruler[] rulers = World.getRulers();
		s.writeInt(rulers.length);
		for (int i = 0; i < rulers.length; i++)
		{
			Ruler ruler = rulers[i];
			if (World.getCurrentTurnRuler() == rulers[i])
				currentTurnRuler = i;
			s.writeObject(ruler);
			s.writeObject(ruler.getScore());
		}
		
		// save the grid
		s.writeObject(World.getInstance().getGrid());
		s.writeInt(World.getInstance().getCurrentRound());
		s.writeInt(currentTurnRuler);
		
		s.flush();
	}

	/* (non-Javadoc)
	 * @see openruler.ui.FileManager.Listener#clear()
	 */
	public void clear()
	{
		doStop();
		doRun();
		
	}

	public void startTurn(int turn, Ruler ruler) throws UserCancelledBattle
	{
	}

	public void endTurn(int turn, Ruler ruler) throws UserCancelledBattle
	{
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
