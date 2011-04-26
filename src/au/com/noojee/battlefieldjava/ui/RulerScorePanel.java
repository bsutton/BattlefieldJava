/*
 * Created on 9/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ui;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import au.com.noojee.battlefieldjava.engine.Ruler;
import au.com.noojee.battlefieldjava.engine.Score;
import au.com.noojee.battlefieldjava.images.ImageLoader;
import au.com.noojee.battlefieldjava.occupants.Castle;


/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RulerScorePanel extends JPanel
{

	private static final int LEFT_LABEL_WIDTH = 115;
	private static final int RIGHT_COL_OFFSET = 120;
	private static final int PANEL_WIDTH = 145;
	/**
	 * 
	 */
	private static final long serialVersionUID = -196615113697204287L;
	private JLabel jIcon = null;
	Ruler ruler;
	private JLabel jRulerName = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7 = null;
	private JLabel jPeasantsKilled = null;
	private JLabel JKnightsKilled = null;
	private JLabel jPeasants = null;
	private JLabel jKnights = null;
	private JLabel jCastles = null;
	private JLabel jLand = null;
	private JLabel jScore = null;
	private JLabel jLabel8 = null;
	private JLabel jLabel10 = null;
	private JLabel jKnightsLost = null;
	private JLabel jPeasantsLost = null;
	/**
	 * 
	 */
	public RulerScorePanel(Ruler ruler)
	{
		super();
		this.ruler = ruler;
		initialize();
		updateScore(ruler.getScore());
		jRulerName.setText(ruler.getRulerName());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jRulerName = new JLabel();
		jPeasantsLost = new JLabel();
		jKnightsLost = new JLabel();
		jLabel10 = new JLabel();
		jLabel8 = new JLabel();
		jScore = new JLabel();
		jLand = new JLabel();
		jCastles = new JLabel();
		jKnights = new JLabel();
		jPeasants = new JLabel();
		JKnightsKilled = new JLabel();
		jPeasantsKilled = new JLabel();
		jLabel7 = new JLabel();
		jLabel6 = new JLabel();
		jLabel5 = new JLabel();
		jLabel4 = new JLabel();
		jLabel3 = new JLabel();
		jLabel2 = new JLabel();
		jLabel1 = new JLabel();
		jIcon = new JLabel();
		this.setLayout(null);
		this.setSize(PANEL_WIDTH, 296);

		jIcon.setBounds(new java.awt.Rectangle(3,1,39,36));
		JPanel background = new JPanel();
		background.setLayout(null);
		background.setLocation(jIcon.getLocation());
		background.setSize(jIcon.getSize());
		background.setBackground(ruler.getColor());
		background.add(jIcon);
		Icon icon = ImageLoader.getIcon(Castle.IMAGE_PATH);
		jIcon.setIcon(icon);
		jRulerName.setText("JLabel");
		jRulerName.setSize(145, 19);
		jRulerName.setLocation(0, 40);
		jLabel1.setText("Score:");
		jLabel2.setText("Land:");
		jLabel2.setPreferredSize(new java.awt.Dimension(31,15));
		jLabel3.setText("Knights:");
		jLabel3.setSize(LEFT_LABEL_WIDTH, 16);
		jLabel3.setLocation(0, 90);
		jLabel4.setText("Peasants:");
		jLabel4.setSize(LEFT_LABEL_WIDTH, 15);
		jLabel4.setLocation(0, 110);
		jLabel5.setText("Castles:");
		jLabel5.setSize(LEFT_LABEL_WIDTH, 15);
		jLabel5.setLocation(1, 69);
		jLabel6.setText("Knights Killed:");
		jLabel6.setSize(LEFT_LABEL_WIDTH, 15);
		jLabel6.setLocation(0, 130);
		jLabel7.setText("Peasants Killed:");
		jLabel7.setSize(LEFT_LABEL_WIDTH, 15);
		jLabel7.setLocation(0, 150);
		jPeasantsKilled.setBounds(RIGHT_COL_OFFSET, 150, 45, 15);
		jPeasantsKilled.setText("JLabel");
		jLabel8.setBounds(0, 170, LEFT_LABEL_WIDTH, 15);
		jLabel8.setText("Knights Lost:");
		jLabel10.setBounds(0, 190, LEFT_LABEL_WIDTH, 15);
		jLabel10.setText("Peasants Lost:");
		jKnightsLost.setBounds(RIGHT_COL_OFFSET, 170, 45, 15);
		jKnightsLost.setText("JLabel");
		jPeasantsLost.setBounds(RIGHT_COL_OFFSET, 190, 45, 15);
		jPeasantsLost.setText("JLabel");
		jLabel2.setBounds(0, 236, 49, 15);
		jLabel1.setBounds(0, 261, 49, 17);
		JKnightsKilled.setBounds(RIGHT_COL_OFFSET, 130, 45, 15);
		JKnightsKilled.setText("JLabel");
		jPeasants.setBounds(RIGHT_COL_OFFSET, 110, 45, 15);
		jPeasants.setText("JLabel");
		jKnights.setBounds(RIGHT_COL_OFFSET, 90, 45, 15);
		jKnights.setText("JLabel");
		jCastles.setBounds(RIGHT_COL_OFFSET, 70, 45, 15);
		jCastles.setText("JLabel");
		jLand.setBounds(RIGHT_COL_OFFSET, 236, 45, 15);
		jLand.setText("JLabel");
		jScore.setBounds(RIGHT_COL_OFFSET, 260, 45, 15);
		jScore.setText("JLabel");
		this.add(jRulerName, null);
		this.add(jLabel1, null);
		this.add(jLabel2, null);
		this.add(jLabel3, null);
		this.add(jLabel4, null);
		this.add(jLabel5, null);
		this.add(jLabel6, null);
		this.add(jLabel7, null);
		this.add(jPeasantsKilled, null);
		this.add(jLabel8, null);
		this.add(jLabel10, null);
		this.add(jKnightsLost, null);
		this.add(jPeasantsLost, null);
		this.add(JKnightsKilled, null);
		this.add(jPeasants, null);
		this.add(jKnights, null);
		this.add(jCastles, null);
		this.add(jLand, null);
		this.add(jScore, null);
		this.add(background, null);
		//this.add(jIcon, null);
		
	}
	public void updateScore(Score score)
	{
		updateValue(jCastles, ruler.getCastles().length);
		updateValue(jKnights, ruler.getKnights().length);
		updateValue(jPeasants, ruler.getPeasants().length);
		updateValue(JKnightsKilled, score.getKnightsKilled());
		updateValue(jPeasantsKilled, score.getPeasantsKilled());
		updateValue(jKnightsLost, score.getKnightsLost());
		updateValue(jPeasantsLost, score.getPeasantsLost());
		updateValue(jLand, ruler.getOwnedLandCount());
		updateValue(jScore, score.getRunningScore());
	}

	/**
	 * @param knightsKilled
	 * @param knightsKilled2
	 */
	private void updateValue(JLabel label, int intValue)
	{
		String value = Integer.toString(intValue);
		if (value.compareToIgnoreCase(label.getText()) != 0)
			label.setText(value);
	}

	/**
	 * @return
	 */
	public Ruler getRuler()
	{
		return ruler;
	}
 }  //  @jve:decl-index=0:visual-constraint="10,10"
